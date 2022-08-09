import re
import os.path
import hashlib
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

        print("Collecting password")
        collectPassword()

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

def collectPassword():
    done = False
    salt = os.urandom(16)
    while(done == False):
         userResponse = input("Please enter a password that:\n"
                    + "- Is at least 10 characters long\n"
                    + "- Contains at least 1 upper case character\n"
                    + "- Contains at least 1 lower case character\n"
                    + "- Contains at least 1 digit\n"
                    + "- Contains at least 1 punctuation mark\n"
                    + "- Does not have 3 consecutive lower case characters\n")
         if(validatePassword(userResponse)):
             password = hashPassword(userResponse, salt)
             createPasswordFile(password)
             userResponse = input("Please reenter password for verification")
             passCheck = hashPassword(userResponse, salt)
             if(verifyPassword(passCheck)):
                 done = True
             else:
                 print("Error: Passwords do not match")
    
         else:
             print("Error: Please enter a valid password (EX: PasSWoRd1!)")
             
def validatePassword(thePassword):
    return regexEngine("^(?=.+[a-z])(?=.+[A-Z])(?=.+[!?.,()\\]\\[{}])(?=.+\\d)(?!.+[a-z][a-z][a-z])(?![a-z][a-z][a-z])[a-zA-z!?.,()\\]\\[{}\\d]{10,}$", thePassword)

def verifyPassword(thePassword):
    f = open("Password.txt", "rb")
    passCheck = f.read()
    if(passCheck == thePassword):
        return True

    else:
        return False
# Code referenced/used from https://nitratine.net/blog/post/how-to-hash-passwords-in-python/
def hashPassword(thePassword, salt):    
    key = hashlib.pbkdf2_hmac("sha256", thePassword.encode("utf-8"), salt, 65536)
    return key + salt
    

def createPasswordFile(thePassword):
    if (os.path.exists("Password.txt")):
        f = open("Password.txt", "wb")
        f.write(thePassword)
    else:
        f = open("Password.txt", "x")
        f = open("Password.txt", "wb")
        f.write(thePassword)
    
    
main()