package com.Hindol.Week20.MCP_Server.tools;

import com.Hindol.Week20.MCP_Server.Entity.SellerAccount;
import com.Hindol.Week20.MCP_Server.Repository.SellerAccountRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerAccountTools {
    private final SellerAccountRepository sellerAccountRepository;

    public SellerAccountTools(SellerAccountRepository sellerAccountRepository) {
        this.sellerAccountRepository = sellerAccountRepository;
    }

    /**
     * Search all seller accounts by name
     * @param name Seller Account Name
     * @return List of Seller Accounts
     */
    @Tool(name = "Search Seller account by name", description = "Find all Seller Accounts by name")
    public String getAccountByName(
            @ToolParam(description = "Seller Account Name") String name) {
        List<SellerAccount> accountList = sellerAccountRepository.findByName(name);
        StringBuilder result = new StringBuilder();
        for (SellerAccount account : accountList) {
            result.append(account.toString()).append("\n");
        }
        return result.toString();
    }

    /**
     * Search all seller accounts by owner
     * @param name Seller account owner
     * @return List of Seller Accounts
     */
    @Tool(name = "Search all Seller account by owner", description = "Find all Seller Accounts by owner")
    public String getAccountByOwner(
            @ToolParam(description = "Seller Account owner") String owner) {
        List<SellerAccount> accountList = sellerAccountRepository.findByOwner(owner);
        StringBuilder result = new StringBuilder();
        for (SellerAccount account : accountList) {
            result.append(account.toString()).append("\n");
        }
        return result.toString();
    }
}
