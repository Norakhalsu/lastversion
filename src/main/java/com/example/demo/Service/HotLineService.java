package com.example.demo.Service;

import com.example.demo.Api.ApiException;
import com.example.demo.Model.HotLine;
import com.example.demo.Model.Requests;
import com.example.demo.Repository.HotLineRepository;
import com.example.demo.Repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotLineService {
    private final HotLineRepository hotLineRepository;
    private final RequestRepository requestRepository;

    public List<HotLine> getAllHotLines() {
       return hotLineRepository.findAll();
    }
    public void addHotLineToSystem(HotLine hotLine) {
        hotLineRepository.save(hotLine);
    }

    public void updateHotLine(Integer hotlineId,HotLine hotLine) {
        HotLine hotLineToUpdate = hotLineRepository.findHotLinesByHotlineId(hotlineId);
        if(hotLineToUpdate == null) {
            throw new ApiException("hotline not found");
        }
        hotLineToUpdate.setDescription(hotLine.getDescription());
        hotLineToUpdate.setTitle(hotLine.getTitle());
        hotLineRepository.save(hotLineToUpdate);
    }
    public void deleteHotLineFromSystem(Integer hotLineId) {
        HotLine hotLine = hotLineRepository.findHotLinesByHotlineId(hotLineId);
        if (hotLine == null) {
            throw new ApiException("HotLine Not Found in System");
        }
        hotLineRepository.delete(hotLine);
    }

    // ---------------------------- end point --------------


    //HOTLINE
    public void statusAcceptRequest(Integer requestId) {
        Requests request = requestRepository.findRequestByRequestId(requestId);
        if (request == null) {
            throw new ApiException("Request Not Found");
        }
        String currentStatus = request.getStatusRequest();
        switch (currentStatus.toLowerCase()) {
            case "accept":
                throw new ApiException("Request already accepted");
            case "canceled":
                throw new ApiException("Request was canceled");
            case "completed":
                throw new ApiException("Request was completed");
            case "pending":
                request.setStatusRequest("Accepted"); // يمكن تعديل هذا حسب هيكل الطلب الخاص بك
                requestRepository.save(request);
                break;
            default:
                throw new ApiException("Invalid request status");
        }
    }


}
