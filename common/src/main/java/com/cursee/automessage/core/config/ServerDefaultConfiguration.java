package com.cursee.automessage.core.config;

import java.util.List;

public class ServerDefaultConfiguration {

    public boolean enabled = false;

    public List<String> messages = List.of(
            "Sent from server after 5 seconds, only once ever",
            "Sent from server every 6 seconds, fives times per session",
            "Sent from server at 2PM, every day",
            "Sent from server at 8PM, only once ever");

    public List<String> links = List.of(
            "https://www.google.com/",
            "https://www.google.com/",
            "https://www.google.com/",
            "https://www.google.com/");

    public List<Long> intervals = List.of(
            5L, 6L, 0L, 0L);

    public List<String> set_times = List.of(
            "", "", "14:00", "20:00");

    public List<Long> soft_limits = List.of(
            1L, 5L, 1L, 1L);

    public List<Long> hard_limits = List.of(
            1L, 0L, 0L, 1L);
}
