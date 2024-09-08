package com.Hindol.Auditing.Client.Implementation;

import com.Hindol.Auditing.Advice.APIResponse;
import com.Hindol.Auditing.Client.DepartmentClient;
import com.Hindol.Auditing.DTO.DepartmentDTO;
import com.Hindol.Auditing.Exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class DepartmentClientImplementation implements DepartmentClient {
    private final RestClient restClient;

    Logger log = LoggerFactory.getLogger(DepartmentClientImplementation.class);

    public DepartmentClientImplementation(@Qualifier("DepartmentServiceClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        log.trace("Trying to retrieve all departments in getAllDepartments");
        try {
            APIResponse<List<DepartmentDTO>> response = restClient.get()
                    .uri("/department").
                    retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new RuntimeException("Could not get the department.");
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            log.debug("Successfully fetched all the departments");
            log.trace("Retrieved departments list in getAllDepartments : {}", response.getData());
            return response.getData();
        }
        catch (Exception e) {
            log.error("Exception occurred in getAllDepartments", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public DepartmentDTO getDepartmentById(Long departmentId) {
        log.trace("Trying to get Department by ID : {} in getDepartmentById", departmentId);
        try {
            APIResponse<DepartmentDTO> response = restClient.get()
                    .uri("/department/{departmentId}", departmentId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("No Department found with ID : " + departmentId);
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            return response.getData();
        }
        catch (Exception e) {
            log.error("Exception occurred in getDepartmentById", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public DepartmentDTO createNewDepartment(DepartmentDTO departmentDTO) {
        log.trace("Trying to create Department with information : {}", departmentDTO);
        try {
            ResponseEntity<APIResponse<DepartmentDTO>> response = restClient.post()
                    .uri("/department")
                    .body(departmentDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.debug("4XXClient error occurred during createNewDepartment");
                        log.error(new String(res.getBody().readAllBytes()));
                        throw new RuntimeException("Unable to create Department");
                    })
                    .toEntity(new ParameterizedTypeReference<>() {
                    });
            log.trace("Successfully created the new department : {}", response.getBody().getData());
            return response.getBody().getData();
        }
        catch (Exception e) {
            log.error("Exception occurred in createNewDepartment", e);
            throw new RuntimeException(e);
        }
    }
}
