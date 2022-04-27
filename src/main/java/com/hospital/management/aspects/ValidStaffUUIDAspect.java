package com.hospital.management.aspects;

import com.hospital.management.enums.ResponseCode;
import com.hospital.management.exceptions.BadRequestException;
import com.hospital.management.repositories.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidStaffUUIDAspect {

    private final StaffRepository staffRepository;

    @Before(value = "@annotation(com.hospital.management.annotations.ValidStaffUUID)")
    public void validateStaffUUID() throws BadRequestException {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String staffUUID = request.getHeader("staffUUID");
        boolean staffExists = staffRepository.existsByUuidIgnoreCase(staffUUID);

        if (!staffExists) {
            throw new BadRequestException(ResponseCode.INVALID_STAFF_UUID);
        }
    }
}
