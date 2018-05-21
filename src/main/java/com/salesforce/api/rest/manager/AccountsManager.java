package com.salesforce.api.rest.manager;

import java.util.ArrayList;
import java.util.List;

import com.orasi.api.restServices.RestResponse;
import com.salesforce.api.domain.Account;
import com.salesforce.api.rest.SFResponse;
import com.salesforce.api.rest.processor.AccountProcessor;

public class AccountsManager {
    private AccountProcessor processor = new AccountProcessor();
    private static ThreadLocal<AccountsManager> instance = new ThreadLocal<>();
    private List<Account> accounts = new ArrayList<>();

    public static AccountsManager getInstance() {
        if (instance == null || instance.get() == null) {
            instance.set(new AccountsManager());
        }
        return instance.get();
    }

    public Account createDefaultAccount() {
        return createDefaultAccount("REST API Test");
    }

    public Account createDefaultAccount(String name) {
        Account account = new Account();
        account.setName(name);
        account.setDescription("This account was created via REST automation");
        account.setPhone("3363363366");
        Account newAccount = create(account);
        accounts.add(newAccount);
        return newAccount;
    }

    public Account createAccount(Account account) {
        Account newAccount = create(account);
        accounts.add(newAccount);
        return newAccount;
    }

    public Account getAccount() {
        return accounts.get(0);
    }

    public Account getAccountByName(String name) {
        return accounts.stream().findFirst().filter(account -> account.getName().equalsIgnoreCase(name)).orElse(null);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void deleteAllAccounts() {
        accounts.stream().forEach(account -> processor.deleteAccount(account));
    }

    public void deleteAccount(Account account) {
        processor.deleteAccount(account);
    }

    public void deleteAccount(String id) {
        processor.deleteAccount(id);
    }

    private Account create(Account account) {
        RestResponse createResponse = processor.createAccount(account);

        SFResponse create = createResponse.mapJSONToObject(SFResponse.class);

        RestResponse getResponse = processor.getAccount(create.getId());
        return getResponse.mapJSONToObject(Account.class);
    }
}
