#!/bin/python2
import string
import fileinput
import re

# file scope number redaction
def redact_numbers(transcript_file):
    for line in fileinput.input(transcript_file, inplace=True, backup='.bak'):
        print(re.sub("\d", "X", line))

# clear non ascii characters
def clear_nonascii(char_string):
    return "".join(i for i in char_string if ord(i)<128)

# import words from a file into list
def get_bank_words(bank_file):
    with open(bank_file) as f:
        words = [line.rstrip('\n') for line in f]
    return set(words)

# import words from a call transcript with optional redaction
def get_transcript_words(transcript_file, redact, verbose):
    with open(transcript_file) as f:
        words = []
        for line in f:
            for word in line.split():
                word = word.strip()
                word = clear_nonascii(word)
                if (redact):
                    word = re.sub("\d", "X", word)
                word = word.translate(None, string.punctuation)
                word = word.lower()
                words.append(word)
    if (verbose):
        print('\nOriginal call transcript:')
        print(words)
    return words

# get subset of like words between two sets
def get_same_words(keywords, words, verbose):
    subset = words.intersection(keywords)
    if (verbose):
        print('\nKeywords found in transcript: ')
        print(subset)
        print('\n')
    return subset

# remove common words in English language
def remove_common_words(common, transcript, verbose):
    subset = transcript.difference(common)
    if (verbose):
        print('\nReduced call transcript:')
        print(subset)
    return subset

# get count of word occurence in list
def get_occurrence_count(word, bank):
    return bank.count(word)

# calculate perceptage of high risk words in reduced transcript
def get_percentage_subset(subset, master, reduced_transcript_len):
    total = 0
    for word in subset:
        total = total + get_occurrence_count(word, master)
    percent = (float(total) / reduced_transcript_len)
    print('Match percentage: ' + str(percent) + '\n')
    return percent

# primary methohd for phishing scam analysis
def is_phishing(keyword_file, transcript, common_file, redact, verbose):
    key_words = get_bank_words(keyword_file)
    common_words = get_bank_words(common_file)
    
    call_words_list = get_transcript_words(transcript, redact, verbose)
    call_words_set = set(call_words_list)
    
    strict_call_words = remove_common_words(common_words, call_words_set, verbose)
    high_risk_words = get_same_words(key_words, strict_call_words, verbose)
    
    match_percent = get_percentage_subset(high_risk_words, call_words_list, len(strict_call_words))

# run tests
def main():

    redact = True
    verbose = False
    
    print('Test 1:')
    is_phishing('keybank/keybank_f1.txt', 'transcripts/my_tests/test1.txt', 'keybank/common_words.txt', redact, verbose)

    print('\nTest 2 - Full:')
    is_phishing('keybank/keybank_f1.txt', 'transcripts/my_tests/test2_long.txt', 'keybank/common_words.txt', redact, verbose)

    print('\nTest 2 - Short:')
    is_phishing('keybank/keybank_f1.txt', 'transcripts/my_tests/test2_short.txt', 'keybank/common_words.txt', redact, verbose)

    print('\nTest 3 - Full:')
    is_phishing('keybank/keybank_f1.txt', 'transcripts/my_tests/test3_long.txt', 'keybank/common_words.txt', redact, verbose)
    
    print('\nTest 3 - Short:')
    is_phishing('keybank/keybank_f1.txt', 'transcripts/my_tests/test3_short.txt', 'keybank/common_words.txt', redact, verbose)

    print('\nTest 4:')
    is_phishing('keybank/keybank_f1.txt', 'transcripts/my_tests/test4.txt', 'keybank/common_words.txt', redact, verbose)

    print('\nTest 5:')
    is_phishing('keybank/keybank_f1.txt', 'transcripts/my_tests/test5.txt', 'keybank/common_words.txt', redact, verbose)


main()

