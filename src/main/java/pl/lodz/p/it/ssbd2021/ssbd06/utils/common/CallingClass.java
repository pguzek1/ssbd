package pl.lodz.p.it.ssbd2021.ssbd06.utils.common;

public interface CallingClass {
    String getTransactionId();
    boolean isLastTransactionRollback();
}