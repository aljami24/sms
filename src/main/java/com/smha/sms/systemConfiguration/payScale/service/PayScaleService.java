package com.smha.sms.systemConfiguration.payScale.service;

import com.smha.sms.systemConfiguration.payScale.model.dto.request.PayScaleRequestDto;
import com.smha.sms.systemConfiguration.payScale.model.dto.response.PayScaleResponseDto;
import com.smha.sms.systemConfiguration.payScale.model.entity.PayScale;
import com.smha.sms.systemConfiguration.payScale.model.mapper.PayScaleMapper;
import com.smha.sms.systemConfiguration.payScale.model.repository.PayScaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayScaleService {

    private final PayScaleRepository payScaleRepository;
    private final PayScaleMapper payScaleMapper;

    public void savePayScale(PayScaleRequestDto payScaleRequestDto) {
        PayScale payScale = new PayScale();
        payScaleMapper.mapDtoToEntity(payScale, payScaleRequestDto);
        payScaleRepository.save(payScale);
    }

    public Page<PayScaleResponseDto> payScaleList(int page, int pageSize, String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortOrder), sortField);
        PageRequest pageable = PageRequest.of(page, pageSize, sort);
        Page<PayScale> payScale = payScaleRepository.findAll(pageable);
        return payScale.map(payScaleMapper::mapToPayScaleResponse);
    }

    public PayScaleRequestDto getPayScaleById(Long id) {
        return payScaleRepository.findById(id)
                .map(payScaleMapper::mapToPayScaleRequestDto)
                .orElse(null);
    }

    public void updatePayScale(Long id, PayScaleRequestDto payScaleRequestDto) {
        PayScale payScale = payScaleRepository.findById(id).orElse(null);
        if (payScale == null) {
            return;
        }
        payScaleMapper.mapDtoToEntity(payScale, payScaleRequestDto);
        payScaleRepository.save(payScale);
    }

    public void deletePayScale(Long id) {
        payScaleRepository.deleteById(id);
    }
}
