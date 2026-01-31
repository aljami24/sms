package com.smha.sms.employee.model.spacification;

import com.smha.sms.common.address.entity.Address;
import com.smha.sms.employee.model.dto.EmployeeFilter;
import com.smha.sms.employee.model.entity.Employee;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {

    public static Specification<Employee> filter(EmployeeFilter filter) {
        return (root, query, cb) -> {


            List<Predicate> predicates = new ArrayList<>();

            // JOIN address
            Join<Employee, Address> addressJoin = root.join("address", JoinType.LEFT);

            if (filter.getEmployeeId() != null){
                predicates.add(
                        cb.equal(root.get("employeeId"), filter.getEmployeeId())
                );
            }

            if (filter.getName() != null && !filter.getName().isEmpty()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + filter.getName().toLowerCase() + "%"
                        )
                );
            }

            if (filter.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), filter.getGender()));
            }

            if (filter.getEmployeeType() != null) {
                predicates.add(cb.equal(root.get("employeeType"), filter.getEmployeeType()));
            }

            if (filter.getIdentityNumber() != null && !filter.getIdentityNumber().isEmpty()){
                predicates.add(cb.equal(root.get("identityNumber"), filter.getIdentityNumber()));
            }

            if (filter.getPhoneNumber() != null && !filter.getPhoneNumber().isEmpty()) {
                predicates.add(cb.equal(root.get("phoneNumber"), filter.getPhoneNumber()));
            }

            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }

            if (filter.getDivisionId() != null) {
                predicates.add(
                        cb.equal(addressJoin.get("division").get("id"), filter.getDivisionId())
                );
            }

            if (filter.getDistrictId() != null) {
                predicates.add(
                        cb.equal(addressJoin.get("district").get("id"), filter.getDistrictId())
                );
            }

            if (filter.getPoliceStationId() != null) {
                predicates.add(
                        cb.equal(addressJoin.get("policeStation").get("id"), filter.getPoliceStationId())
                );
            }

            query.distinct(true); // duplicate employee ঠেকানোর জন্য

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
