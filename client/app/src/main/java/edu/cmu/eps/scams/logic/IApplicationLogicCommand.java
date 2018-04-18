package edu.cmu.eps.scams.logic;

/**
 * Command for running application logic.
 */
public interface IApplicationLogicCommand {
    public ApplicationLogicResult execute(IApplicationLogic logic);
}
