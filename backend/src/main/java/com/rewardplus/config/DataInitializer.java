package com.rewardplus.config;

import com.rewardplus.entity.Role;
import com.rewardplus.entity.User;
import com.rewardplus.entity.Customer;
import com.rewardplus.entity.LoyaltyAccount;
import com.rewardplus.entity.MembershipLevel;
import com.rewardplus.repository.RoleRepository;
import com.rewardplus.repository.UserRepository;
import com.rewardplus.repository.CustomerRepository;
import com.rewardplus.repository.LoyaltyAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final LoyaltyAccountRepository loyaltyAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initialize() {
        // 1. Initialize Roles
        List<String> requiredRoles = List.of("ADMIN", "MANAGER", "SALES", "MARKETING", "CUSTOMER");
        for (String roleName : requiredRoles) {
            roleRepository.findByRoleName(roleName)
                    .orElseGet(() -> roleRepository.save(createRole(roleName)));
        }

        // 2. Initialize Default Users
        seedUser("admin", "admin123", "admin@rewardplus.com", "System", "Admin", "ADMIN");
        seedUser("manager", "manager123", "manager@rewardplus.com", "Business", "Manager", "MANAGER");
        seedUser("sales", "sales123", "sales@rewardplus.com", "Sales", "Rep", "SALES");
        seedUser("marketing", "marketing123", "marketing@rewardplus.com", "Marketing", "Exec", "MARKETING");
        
        // Seed customer user with profile & loyalty account
        seedCustomerUser("customer", "customer123", "customer@rewardplus.com", "Jane", "Doe", "1234567890");
    }

    private Role createRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        role.setDescription(roleName + " role");
        role.setActive(true);
        return role;
    }

    private void seedUser(String username, String password, String email, String firstName, String lastName, String roleName) {
        if (!userRepository.existsByUsername(username)) {
            Role role = roleRepository.findByRoleName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));

            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setActive(true);
            user.setRoles(new HashSet<>(Set.of(role)));

            userRepository.save(user);
        }
    }

    private void seedCustomerUser(String username, String password, String email, String firstName, String lastName, String phone) {
        if (!userRepository.existsByUsername(username)) {
            Role role = roleRepository.findByRoleName("CUSTOMER")
                    .orElseThrow(() -> new RuntimeException("CUSTOMER role not found"));

            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setActive(true);
            user.setRoles(new HashSet<>(Set.of(role)));

            User savedUser = userRepository.save(user);

            // Create customer profile if not exists
            if (!customerRepository.existsByPhone(phone)) {
                Customer customer = new Customer();
                customer.setUser(savedUser);
                customer.setCustomerCode("CUST" + (System.currentTimeMillis() % 100000));
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setPhone(phone);
                customer.setDateOfBirth(LocalDate.of(1995, 5, 15));
                customer.setAddress("123 Enterprise Way");
                customer.setCity("Metropolis");
                customer.setState("NY");
                customer.setCountry("USA");
                customer.setPinCode("10001");
                customer.setMembershipLevel(MembershipLevel.BRONZE);
                customer.setCurrentPoints(100); // give some starting points
                customer.setActive(true);

                Customer savedCustomer = customerRepository.save(customer);

                // Create loyalty account
                LoyaltyAccount loyaltyAccount = new LoyaltyAccount();
                loyaltyAccount.setCustomer(savedCustomer);
                loyaltyAccount.setCurrentPoints(100);
                loyaltyAccount.setAvailablePoints(100);
                loyaltyAccount.setLifetimePoints(100);
                loyaltyAccount.setMembershipLevel(MembershipLevel.BRONZE);
                loyaltyAccount.setActive(true);

                loyaltyAccountRepository.save(loyaltyAccount);
            }
        }
    }
}
