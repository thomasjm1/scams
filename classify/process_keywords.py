import string

#TODO
#send to database
#python encryption and decryption
#speech to text translation?

def clear_nonascii(char_string):
    return "".join(i for i in char_string if ord(i)<128)


def get_bank_words(bank_file):
    with open(bank_file) as f:
        words = [line.rstrip('\n') for line in f]
    return set(words)


def get_transcript_words(transcript_file, verbose):
    with open(transcript_file) as f:
        words = []
        for line in f:
            for word in line.split():
                word = word.strip()
                word = clear_nonascii(word)
                word = word.translate(None, string.punctuation)
                word = word.lower()
                words.append(word)
    if (verbose):
        print('\nOriginal call transcript:')
        print(words)
    return words


def get_same_words(keywords, words, verbose):
    subset = words.intersection(keywords)
    if (verbose):
        print('\nKeywords found in transcript: ')
        print(subset)
        print('\n')
    return subset


def remove_common_words(common, transcript, verbose):
    subset = transcript.difference(common)
    if (verbose):
        print('\nReduced call transcript:')
        print(subset)
    return subset


def get_occurrence_count(word, bank):
    return bank.count(word)


def get_percentage_subset(subset, master, reduced_transcript_len):
    total = 0
    for word in subset:
        total = total + get_occurrence_count(word, master)
    percent = (float(total) / reduced_transcript_len)
    print('Match percentage: ' + str(percent) + '\n')
    return percent


def is_phishing(keyword_file, transcript, common_file, verbose):
    key_words = get_bank_words(keyword_file)
    common_words = get_bank_words(common_file)
    
    call_words_list = get_transcript_words(transcript, verbose)
    call_words_set = set(call_words_list)
    
    strict_call_words = remove_common_words(common_words, call_words_set, verbose)
    high_risk_words = get_same_words(key_words, strict_call_words, verbose)
    
    match_percent = get_percentage_subset(high_risk_words, call_words_list, len(strict_call_words))
    

def main():

    verbose = False
    
    print('Test 1:')
    is_phishing('keybank/keybank_f1.txt', 'transcripts/test1.txt', 'keybank/common_words.txt', verbose)

    print('\nTest 2 - Full:')
    is_phishing('keybank/keybank_f1.txt', 'transcripts/test2_long.txt', 'keybank/common_words.txt', verbose)

    print('\nTest 2 - Short:')
    is_phishing('keybank/keybank_f1.txt', 'transcripts/test2_short.txt', 'keybank/common_words.txt', verbose)

    print('\nTest 3 - Full:')
    is_phishing('keybank/keybank_f1.txt', 'transcripts/test3_long.txt', 'keybank/common_words.txt', verbose)
    
    print('\nTest 3 - Short:')
    is_phishing('keybank/keybank_f1.txt', 'transcripts/test3_short.txt', 'keybank/common_words.txt', verbose)

    print('\nTest 4:')
    is_phishing('keybank/keybank_f1.txt', 'transcripts/test4.txt', 'keybank/common_words.txt', verbose)

    print('\nTest 5:')
    is_phishing('keybank/keybank_f1.txt', 'transcripts/test5.txt', 'keybank/common_words.txt', verbose)


main()

