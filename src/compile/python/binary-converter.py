def byteList(path):
    f = file(path)
    contents = f.read()

    l = []
    for c in list(contents):
        l.append(ord(c))

    return l

if __name__ == "__main__":
    import sys

    if (len(sys.argv) == 1):
        print("Usage: binary-converter <file>")
        exit()

    filePath = sys.argv[1]
    print(byteList(filePath))
