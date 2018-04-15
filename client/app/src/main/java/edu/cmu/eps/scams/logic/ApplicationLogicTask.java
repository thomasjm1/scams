package edu.cmu.eps.scams.logic;

import android.os.AsyncTask;

public class ApplicationLogicTask extends AsyncTask<IApplicationLogicCommand, Integer, ApplicationLogicResult> {

    private final IApplicationLogic logic;
    private final TaskProgressCommand progressCommand;
    private final TaskCompletionCommand completionCommand;

    public ApplicationLogicTask(IApplicationLogic logic, TaskProgressCommand progressCommand, TaskCompletionCommand completionCommand) {
        this.logic = logic;
        this.progressCommand = progressCommand;
        this.completionCommand = completionCommand;
    }

    @Override
    protected ApplicationLogicResult doInBackground(IApplicationLogicCommand... command) {
        return command[0].execute(this.logic);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        this.progressCommand.execute(progress[0]);
    }

    @Override
    protected void onPostExecute(ApplicationLogicResult result) {
        this.completionCommand.execute(result);

    }
}
