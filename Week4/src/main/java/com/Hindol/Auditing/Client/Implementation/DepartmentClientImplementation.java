package com.Hindol.Auditing.Client.Implementation;

import com.Hindol.Auditing.Advice.APIResponse;
import com.Hindol.Auditing.Client.DepartmentClient;
import com.Hindol.Auditing.DTO.DepartmentDTO;
import com.Hindol.Auditing.Exception.ResourceNotFoundException;
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

    public DepartmentClientImplementation(@Qualifier("DepartmentServiceClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        try {
            APIResponse<List<DepartmentDTO>> response = restClient.get()
                    .uri("/department").
                    retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        throw new RuntimeException("Could not get the department.");
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            return response.getData();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DepartmentDTO getDepartmentById(Long departmentId) {
        try {
            APIResponse<DepartmentDTO> response = restClient.get()
                    .uri("/department/{departmentId}", 1)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        throw new ResourceNotFoundException("No Department found with ID : 1");
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            return response.getData();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DepartmentDTO createNewDepartment(DepartmentDTO departmentDTO) {
        try {
            ResponseEntity<APIResponse<DepartmentDTO>> response = restClient.post()
                    .uri("/department")
                    .body(departmentDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        throw new RuntimeException("Unable to create Department");
                    })
                    .toEntity(new ParameterizedTypeReference<>() {
                    });
            return response.getBody().getData();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
