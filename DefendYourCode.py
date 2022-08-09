import re
import os.path
def main():

    #For testing purposes, just remove the loop
    for x in range(10):
        userName = collectName()

        print("Collecting Integer 1")
        integer1 = collectInteger()
        print("Collecting Integer 2")
        integer2 = collectInteger()

        print("Collecting input file")
        inputFile = collectFileName()

        print("Collecting output file")
        outputFile = collectFileName()



def collectName():

    while(True):
        userResponse = input("Please enter in your name: ")

        if(validateName(userResponse)):
            return userResponse
        else:
            print("Error: Please enter a name EX \"Varun Parbhakar\" ")


def validateName(theUserName):
    regex = "[a-zA-Z]{2,50} [a-zA-Z]{2,50}"
    return regexEngine(regex, theUserName)


def collectInteger():

    while (True):
        userResponse = input("Please enter your integer: ")

        if (validateInteger(userResponse)):
            if(int(userResponse) <= 2147483647 and int(userResponse) >= -2147483648):
                return int(userResponse)
            else:
                return False
        else:
            print("Error: Please enter an integer in the range of 4 bit integer")


def validateInteger(theInteger):
    if(regexEngine("(-)?\\d{1,10}", theInteger)):
        try:
            int(theInteger)
        except:
            return False

        return True
    else:
        return False

def regexEngine(regex, theString):
    pattern = re.compile(regex)

    return re.fullmatch(pattern, theString)

def collectFileName():
    while(True):
        userResponse = input("Please enter a file name: ")

        if(validateFileName(userResponse)):
            return userResponse
        else:
            print("Error: Please enter a valid file name EX: input.txt")


def validateFileName(theFileName):
    if(regexEngine("[a-zA-Z0-9()\\[\\]\\/\\?\\-]{1,31}.txt",theFileName)):
        try:
            file = os.path.exists(theFileName)
            if(not file):
                print("This file does not exist")
                return False
        except:
            return False

        return True

    else:
        return False

main()