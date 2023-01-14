package ua.edu.znu.travelagencyweb.model.dto;

import lombok.Data;

/**
 * DTO for tourassignment.html,
 * used in the {@link ua.edu.znu.travelagencyweb.controller.HomeServlet}.
 */
@Data
public class TourAssignment {
    private Long tourId;
    private String tourDeparture;
    private String tourArrival;
    private String tourTransport;
    private String employeesInfo;
    private String clientsInfo;
}