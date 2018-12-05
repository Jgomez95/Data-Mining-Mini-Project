from sklearn import datasets
from sklearn import tree
from sklearn import svm
from sklearn.svm import SVC
import os
trainPath = '/Users/Juan/Projects/Data-Mining-Mini-Project/train'
testPath = '/Users/Juan/Projects/Data-Mining-Mini-Project/test'

def main():
  svm_classifier(trainPath, testPath)

def svm_classifier(file_path_training, file_path_test):
    # declartion of all list need in this section
    allElements = []
    allWords = []
    labels = []
    allWordsTest = []
    allElementsTest = []
    countTest = []
    i = 0
    j = 0
    #********************************************
    #read in the train data
    for filename in os.listdir(file_path_training):
        if filename.startswith("spm"):
            #adds 1 for spam to labels list
            labels.append(1)
        else:
            labels.append(0)
        #reads all files and saves every word to allWords list
        with open((trainPath+"/"+filename), 'r') as f:
            allElements.append(f.read())
            for line in f:
                for word in line.split():
                    allWords.append(word)

    # change allWords list to dictionary to purge a duliplicate words
    #allWords = set(allWords)
    count = [0] * len(allWords)
    for email in allElements:
        for word in allWords:
            count[i] = email.count(word)
        i += 1
    #********************************************
    #read in the test data
    for filename in os.listdir(file_path_test):
        splitWords = filename.split(' ')
        allElementsTest.append(splitWords)
        with open((testPath+"/"+filename), 'r') as f:
            for line in f:
                for word in line.split():
                    allWordsTest.append(word)
    # change allWordsTest list to dictionary to purge a duliplicate words
    allWordsTest = set(allWordsTest)
    # iterates through words and gets a count of all the words in the emails
    for email in allElementsTest:
        countTest.append([])
        for word in allWordsTest:
            countTest[i].append(email.count(word))
        j += 1

    #prints if successful
    print("successful reading in test data & train data")
    #SVM classifer training set
    clf = svm.SVC(gamma='auto', C=1.0, class_weight=None, kernel='linear', verbose=False, degree=3)
    clf.fit(count, labels)

    # run the svm prediction method with our test data
    #print( clf.predict(countTest))

main()
