package com.smha.sms.systemConfiguration.fee.service;

import com.smha.sms.systemConfiguration.fee.model.dto.request.FeeRequestDto;
import com.smha.sms.systemConfiguration.fee.model.dto.response.FeeResponseDto;
import com.smha.sms.systemConfiguration.fee.model.entity.Fee;
import com.smha.sms.systemConfiguration.fee.model.mapper.FeeMapper;
import com.smha.sms.systemConfiguration.fee.model.repository.FeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeeService {
    private final FeeRepository feeRepository;
    private final FeeMapper feeMapper;

    public void saveFee(FeeRequestDto feeRequestDto) {
        Fee fee = new Fee();
        feeMapper.mapDtoToEntity(fee, feeRequestDto);
        feeRepository.save(fee);
    }

    public Page<FeeResponseDto> feeList(int page, int pageSize, String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortOrder), sortField);
        PageRequest pageable = PageRequest.of(page, pageSize, sort);
        Page<Fee> fee = feeRepository.findAll(pageable);
        return fee.map(feeMapper::mapToFeeResponse);
    }

    public FeeRequestDto getFeeById(Long id) {
        return feeRepository.findById(id)
                .map(feeMapper::mapToFeeRequestDto)
                .orElse(null);
    }

    public void updateFee(Long id, FeeRequestDto feeRequestDto) {
        Fee fee = feeRepository.findById(id).orElse(null);
        if (fee == null) {
            return;
        }
        feeMapper.mapDtoToEntity(fee, feeRequestDto);
        feeRepository.save(fee);
    }

    public void deleteFee(Long id) {
        feeRepository.deleteById(id);
    }
}
