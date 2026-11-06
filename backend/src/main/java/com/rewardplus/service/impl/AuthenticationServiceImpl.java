package com.rewardplus.service.impl;

import com.rewardplus.dto.request.CustomerRegistrationRequest;
import com.rewardplus.dto.request.LoginRequest;
import com.rewardplus.dto.response.CustomerRegistrationResponse;
import com.rewardplus.dto.response.LoginResponse;
import com.rewardplus.entity.Customer;
import com.rewardplus.entity.LoyaltyAccount;
import com.rewardplus.entity.MembershipLevel;
import com.rewardplus.entity.Role;
import com.rewardplus.entity.User;
import com.rewardplus.exception.EmailAlreadyExistsException;
import com.rewardplus.exception.PhoneAlreadyExistsException;
import com.rewardplus.exception.RoleNotFoundException;
import com.rewardplus.exception.UserAlreadyExistsException;
import com.rewardplus.exception.UserNotFoundException;
import com.rewardplus.repository.CustomerRepository;
import com.rewardplus.repository.LoyaltyAccountRepository;
import com.rewardplus.repository.RoleRepository;
import com.rewardplus.repository.UserRepository;
import com.rewardplus.security.CustomUserDetailsService;
import com.rewardplus.security.JwtService;
import com.rewardplus.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtService jwtService;

    private final UserRepository userRepository;
 
    private final RoleRepository roleRepository;

    private final CustomerRepository customerRepository;

    private final LoyaltyAccountRepository loyaltyAccountRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(
                        request.getUsername());

        String token = jwtService.generateToken(userDetails);

        User user = userRepository.findByUsername(
                        request.getUsername())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        String role = user.getRoles()
                .stream()
                .findFirst()
                .map(Role::getRoleName) 
                .orElse("USER"); 

        return new LoginResponse(
                token,
                user.getUsername(),
                role
        );
    }

    @Override
    @Transactional
    public CustomerRegistrationResponse registerCustomer(
            CustomerRegistrationRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(
                    "Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email already exists");
        }

        if (customerRepository.existsByPhone(request.getPhone())) {
            throw new PhoneAlreadyExistsException(
                    "Phone number already exists");
        }

        Role customerRole = roleRepository.findByRoleName("CUSTOMER")
                .orElseThrow(() ->
                        new RoleNotFoundException(
                                "Customer role not found. Please create CUSTOMER role before registration."));

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setActive(true);

        user.getRoles().add(customerRole);

        User savedUser = userRepository.save(user);

        Customer customer = new Customer();

        customer.setCustomerCode(
                generateCustomerCode(savedUser.getId()));

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setPhone(request.getPhone());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setCountry(request.getCountry());
        customer.setPinCode(request.getPinCode());

        customer.setMembershipLevel(MembershipLevel.BRONZE);
        customer.setCurrentPoints(0);
        customer.setActive(true);

        customer.setUser(savedUser);

        Customer savedCustomer =
                customerRepository.save(customer);

        // Create Loyalty Account automatically
        LoyaltyAccount loyaltyAccount = new LoyaltyAccount();

        loyaltyAccount.setCustomer(savedCustomer);
        loyaltyAccount.setCurrentPoints(0);
        loyaltyAccount.setAvailablePoints(0);
        loyaltyAccount.setLifetimePoints(0);
        loyaltyAccount.setMembershipLevel(MembershipLevel.BRONZE);
        loyaltyAccount.setActive(true);

        loyaltyAccountRepository.save(loyaltyAccount);

        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(
                        savedUser.getUsername());

        String token = jwtService.generateToken(userDetails);

        return new CustomerRegistrationResponse(
                token,
                savedUser.getUsername(),
                customerRole.getRoleName(),
                savedCustomer.getId()
        );
    }

    private String generateCustomerCode(Long userId) {

        return "CUST-" + userId + "-" + System.currentTimeMillis();
    }
}