package catgirlmod.interfaces;

import basemod.interfaces.ISubscriber;

public interface IncrementDiscardSubscriber extends ISubscriber {
    void receiveIncrementDiscard(boolean endOfTurn);
}
