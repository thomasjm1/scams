package edu.cmu.eps.scams.classify;

import java.util.*;
import edu.cmu.eps.scams.logic.model.ClassifierParameters;

public class ClassifyFacade {
  
    //List of common words in the English language - these don't add value during processing, so are stripped from the transcript
    public static List<String> common_words = new ArrayList<String>(
    Arrays.asList("the","be","is","are","to","of","and","a","in","that","have","i","it","for","not","on","with","he","as","you","do","at","this","but",
      "his","by","from","they","we","say","her","she","or","will","an","my","one","all","would","there","their","what","so","up","out","if","about",
      "who","get","which","go","when","me","make","can","like","time","no","just","him","know","take","person","into","year","your","good","some",
      "could","them","see","other","than","then","now","look","only","come","its","over","think","also","back","after","use","two","how","our","work",
      "first","well","way","even","new","want","because","any","these","give","day","most","us","hi","hello","okay","been","those","was","has","each",
      "did","not"));

    public static String clear_nonascii(String line)
    {
        return line.replaceAll("[^\\x00-\\x7F]", "");
    }
    
    //Extract list of words from a transcript, removing non-ascii characters and numbers
    public static List<String> get_transcript_words(String transcript)
    {
        String line;
        List<String> words = new ArrayList<String>();

        for (String word:transcript.split("\\s+"))
        {
            word = clear_nonascii(word);
            word = word.replaceAll("\\d","");
            word = word.replaceAll("[^a-zA-Z ]", "").toLowerCase(); //to remove punctuation marks
            words.add(word);
        } 

        return words;
    }
    
    //Get the list of scam-indicative keywords actually present in the transcript
    public static List<String> get_same_words(List<String> keywords, List<String> words)
    {
        List<String> subset = new ArrayList<String> (keywords.size() > words.size() ? keywords.size() : words.size());
        subset.addAll(keywords);
        subset.retainAll(words);
        return subset;
    }

    //Remove the common words from the transcript
    public static List<String> remove_common_words(List<String> words)
    {
        List<String> subset = new ArrayList<String> (words.size() > common_words.size() ? words.size() : common_words.size());
        subset.addAll(words);
        subset.removeAll(common_words);
        return subset;
    }
    
    public static int get_occurrence_count(String word, List<String> words)
    {
        return java.util.Collections.frequency(words, word);
    }

    //Get the percentage of words in the transcript that are actually scam-indicative keywords
    public static double get_percentage_subset(List<String> subset, List<String> master, int reduced_transcript_len)
    {
        int total=0;
        double percent;
    
        for(String word:subset)
            total+=get_occurrence_count(word,master);

        try
        {
            percent = (double)total/reduced_transcript_len;
            return percent;
        }

        catch(ArithmeticException e)
        {
            percent = 0.0;
            return percent;
        }
    }
    
    //Get the likelihood that a call is a scam by determining the percentage of words in the transcript that are scam-indicative
    public static double get_scam_likelihood (String transcript, String keywordsobj)
    {
    
        String keywordlist = keywordsobj.get("keywords");
        //String keywordlist = "scam money dollar dollars bank banks late urgent immediate opportunity win deadline deadlines amount rich poor generous charity charities profit profits selected offer offers free bonus bonuses buy valuable prize prizes foreign lottery winner credit card investment investments stock stocks risk risks passport birth certificate social security return trust check order company charge charges shipping travel loan interest irs charitable registration offer savings confirm password user name username account address security key billing sensitive debt collection audit tax taxes court seized seize report unpaid case form license amount license owe confidential bill private arrest arrested warrant unpaid property insurance retirement fund funds emergency cash withdraw fee interest legal lawsuit accountant investment investigation panic revenue deposit wire federal transaction accounting recorded payment assurance risk payments paperwork tax problem federal department internal revenue service legal randomly selected";
        String[] kwarray = keywordlist.split("\\s+");

        List<String> key_words = new ArrayList<String>(Arrays.asList(kwarray));

        List<String> call_words_list = get_transcript_words(transcript);

        List<String> strict_call_words = remove_common_words(call_words_list);
        List<String> high_risk_words = get_same_words(key_words,strict_call_words);

        double match_percent = get_percentage_subset(high_risk_words,call_words_list,strict_call_words.size());

        return (match_percent*100);
    }
    
    //Extract useful words from the transcript of the call to be used later for keyword updation
    public static String extract_info(String transcript, double scam_flag)
    {
        List<String> call_words_list = get_transcript_words(transcript);
        List<String> strict_call_words = remove_common_words(call_words_list);

        StringJoiner joiner = new StringJoiner(" ", "", "");
        strict_call_words.forEach(joiner::add);

        String extracted_words_list = joiner.toString();

        return extracted_words_list;

    }

    //Called once transcript is ready
    public static double isScam(
            String transcript,
            double confidence,
            long callStartTime,
            String phoneNumber,
            ClassifierParameters classifierParameters) {
        
        double result = get_scam_likelihood(transcript, classifierParameters.getContent());
        return result;
    }

    //Called once a day indirectly by the Python keyword bank builder
    public static String extractDetails(
            String transcript,
            double confidence,
            long callStartTime,
            String phoneNumber,
            ClassifierParameters classifierParameters,
            double response) {
       
        String extracted_words = extract_info(transcript, response);
        return extracted_words;
    }
}
