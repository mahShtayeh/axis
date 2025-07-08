package com.axis.account.mapper;

import com.axis.account.dto.AccountDTO;
import com.axis.account.model.Account;
import com.axis.account.web.request.AccountCreationRequest;
import org.mapstruct.Mapper;

/**
 * Axis accounts mapper
 *
 * @author Mahmoud Shtayeh
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {
    /**
     * Map Axis account creation request to DTO
     *
     * @param request Axis account creation request
     * @return Axis Account DTO
     */
    AccountDTO toDTO(AccountCreationRequest request);

    /**
     * Map Axis account details out of DTO to Entity
     *
     * @param accountDTO Axis account details
     * @return Axis account
     */
    Account toEntity(AccountDTO accountDTO);
}