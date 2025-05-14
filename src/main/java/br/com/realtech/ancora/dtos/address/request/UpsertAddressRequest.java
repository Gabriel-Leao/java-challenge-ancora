package br.com.realtech.ancora.dtos.address.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpsertAddressRequest {
    @NotBlank(message = "Street can't be empty")
    @Pattern(regexp = "^[a-zA-Z0-9\\sáàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑ'°-]+$",
            message = "Street contains invalid characters")
    @Size(max = 150, message = "Complement must have at most 150 characters")
    private String street;

    @NotBlank(message = "Number can't be empty")
    @Size(max = 20, message = "Number must have at most 20 characters")
    @Pattern(regexp = "^[0-9]+[a-zA-Z]?$",
            message = "Number must be alphanumeric (e.g., 123 or 123A)")
    private String number;

    @Size(max = 100, message = "Complement must have at most 100 characters")
    private String complement;

    @NotBlank(message = "City can't be empty")
    @Size(max = 100, message = "City must have at most 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\sáàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑ'-]+$",
            message = "City contains invalid characters")
    private String city;

    @Size(min = 2, max = 2, message = "State must have exactly 2 characters")
    @Pattern(regexp = "(?i)^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$",
            message = "Invalid Brazilian state")
    private String state;

    @NotBlank(message = "PostalCode can't be empty")
    @Size(min = 8, max = 8, message = "Postal code must have exactly 8 digits")
    @Pattern(regexp = "^[0-9]{8}$",
            message = "Postal code must contain only numbers")
    private String postalCode;
}
