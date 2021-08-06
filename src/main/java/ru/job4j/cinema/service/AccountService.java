package ru.job4j.cinema.service;

import ru.job4j.cinema.repository.Account;
import ru.job4j.cinema.repository.Storage;

import javax.validation.ConstraintViolationException;

public class AccountService {

    public static Account saveAccount (Account account) {
        return  Storage.instOf().saveAccount(account);
    }

    public static boolean checkAccount(Account account) {
        return Storage.instOf().checkAccount(account);
    }
}
