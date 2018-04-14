package edu.cmu.eps.scams.classify;

import edu.cmu.eps.scams.logic.model.ClassifierParameters;

public class ClassifyFacade {

    public static double isScam(
            String transcript,
            double confidence,
            long callStartTime,
            String phoneNumber,
            ClassifierParameters classifierParameters) {
        return 0.5;
    }

    public static String extractDetails(
            String transcript,
            double confidence,
            long callStartTime,
            String phoneNumber,
            ClassifierParameters classifierParameters,
            double response) {
        return "{\"message\" : \"Hello world\"}";
    }
}
