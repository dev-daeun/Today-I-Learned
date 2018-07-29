import os

# program that renames a bunch of files
def rename_files():
    #(1) get file names from a folder
    file_path = "/home/kde6260/LearningLangs/prank"
    file_list = os.listdir(file_path)
    os.chdir(file_path)
    
    #(2) for each file, rename filename
    # 'dic' is dictionary type. Python's dictionary is quite similar with JSON.
    dic = {}
    # range(a, b) means iterator's min value equals a, max value equals b-1.
    for i in range (0, 10):
        dic[str(i)] = None # str() casts int to str, int() casts int to str.

    # maketrans(param[,]) returns table for str.translate() in Python 3.6.
    # In maketrans(), each type of 'dic' variable's key which is converted to its value is char or int. 
    for file_name in file_list:
        print("old name : "+file_name)
        os.rename(file_name, file_name.translate(file_name.maketrans(dic)))
        print("new name : "+file_name)

    print(file_list)


rename_files()