package org.example;

public class ATMBuilder {
    IMechanismsTransfer transferToClient;
    IMechanismsTransfer transferToSafe;
    ISafe safe;

    public ATMBuilder setTransferToClient(IMechanismsTransfer transferToClient) {
        this.transferToClient = transferToClient;
        return this;
    }

    public ATMBuilder setTransferToSafe(IMechanismsTransfer transferToSafe) {
        this.transferToSafe = transferToSafe;
        return this;
    }

    public ATMBuilder setSafe(ISafe safe) {
        this.safe = safe;
        return this;
    }

    public ATMBaseImpl build() {
        if (transferToClient == null) {
            transferToClient = new MechanismTransferToClient();
        }

        if (transferToSafe == null) {
            transferToSafe = new MechanismTransferToSafe();
        }
        if (safe == null) {
            safe = new Safe(transferToClient);
        }

        return new ATMBaseImpl(safe, transferToSafe);
    }
}
