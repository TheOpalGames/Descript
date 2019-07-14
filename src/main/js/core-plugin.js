var MAIN_CLASSES = ["net.minecraft.client.main.Main", "net.minecraft.server.MinecraftServer", "net.minecraft.data.Main"];
var INIT_BYTECODE = [202, 254, 186, 190, 0, 0, 0, 52, 1, 25, 7, 0, 2, 1, 0, 38, 110, 101, 116, 47, 116, 104, 101, 111, 112, 97, 108, 103, 97, 109, 101, 115, 47, 100, 101, 115, 99, 114, 105, 112, 116, 47, 68, 101, 115, 99, 114, 105, 112, 116, 73, 110, 105, 116, 7, 0, 4, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 1, 0, 9, 106, 97, 118, 97, 69, 110, 116, 114, 121, 1, 0, 3, 40, 41, 86, 1, 0, 10, 69, 120, 99, 101, 112, 116, 105, 111, 110, 115, 7, 0, 9, 1, 0, 19, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 69, 120, 99, 101, 112, 116, 105, 111, 110, 1, 0, 4, 67, 111, 100, 101, 10, 0, 1, 0, 12, 12, 0, 13, 0, 14, 1, 0, 15, 102, 105, 110, 100, 68, 101, 115, 99, 114, 105, 112, 116, 74, 97, 114, 1, 0, 16, 40, 41, 76, 106, 97, 118, 97, 47, 105, 111, 47, 70, 105, 108, 101, 59, 10, 0, 16, 0, 18, 7, 0, 17, 1, 0, 12, 106, 97, 118, 97, 47, 105, 111, 47, 70, 105, 108, 101, 12, 0, 19, 0, 20, 1, 0, 5, 116, 111, 85, 82, 73, 1, 0, 16, 40, 41, 76, 106, 97, 118, 97, 47, 110, 101, 116, 47, 85, 82, 73, 59, 10, 0, 22, 0, 24, 7, 0, 23, 1, 0, 12, 106, 97, 118, 97, 47, 110, 101, 116, 47, 85, 82, 73, 12, 0, 25, 0, 26, 1, 0, 5, 116, 111, 85, 82, 76, 1, 0, 16, 40, 41, 76, 106, 97, 118, 97, 47, 110, 101, 116, 47, 85, 82, 76, 59, 7, 0, 28, 1, 0, 23, 106, 97, 118, 97, 47, 110, 101, 116, 47, 85, 82, 76, 67, 108, 97, 115, 115, 76, 111, 97, 100, 101, 114, 7, 0, 30, 1, 0, 12, 106, 97, 118, 97, 47, 110, 101, 116, 47, 85, 82, 76, 10, 0, 27, 0, 32, 12, 0, 33, 0, 34, 1, 0, 6, 60, 105, 110, 105, 116, 62, 1, 0, 18, 40, 91, 76, 106, 97, 118, 97, 47, 110, 101, 116, 47, 85, 82, 76, 59, 41, 86, 8, 0, 36, 1, 0, 39, 110, 101, 116, 46, 116, 104, 101, 111, 112, 97, 108, 103, 97, 109, 101, 115, 46, 100, 101, 115, 99, 114, 105, 112, 116, 46, 67, 111, 114, 101, 77, 111, 100, 76, 111, 97, 100, 101, 114, 10, 0, 38, 0, 40, 7, 0, 39, 1, 0, 15, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 67, 108, 97, 115, 115, 12, 0, 41, 0, 42, 1, 0, 7, 102, 111, 114, 78, 97, 109, 101, 1, 0, 61, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 90, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 67, 108, 97, 115, 115, 76, 111, 97, 100, 101, 114, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 67, 108, 97, 115, 115, 59, 8, 0, 44, 1, 0, 19, 108, 111, 97, 100, 68, 101, 115, 99, 114, 105, 112, 116, 80, 108, 117, 103, 105, 110, 115, 10, 0, 38, 0, 46, 12, 0, 47, 0, 48, 1, 0, 17, 103, 101, 116, 68, 101, 99, 108, 97, 114, 101, 100, 77, 101, 116, 104, 111, 100, 1, 0, 64, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 91, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 67, 108, 97, 115, 115, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 114, 101, 102, 108, 101, 99, 116, 47, 77, 101, 116, 104, 111, 100, 59, 10, 0, 50, 0, 52, 7, 0, 51, 1, 0, 24, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 114, 101, 102, 108, 101, 99, 116, 47, 77, 101, 116, 104, 111, 100, 12, 0, 53, 0, 54, 1, 0, 6, 105, 110, 118, 111, 107, 101, 1, 0, 57, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 91, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 1, 0, 15, 76, 105, 110, 101, 78, 117, 109, 98, 101, 114, 84, 97, 98, 108, 101, 1, 0, 18, 76, 111, 99, 97, 108, 86, 97, 114, 105, 97, 98, 108, 101, 84, 97, 98, 108, 101, 1, 0, 11, 100, 101, 115, 99, 114, 105, 112, 116, 74, 97, 114, 1, 0, 14, 76, 106, 97, 118, 97, 47, 105, 111, 47, 70, 105, 108, 101, 59, 1, 0, 3, 117, 114, 108, 1, 0, 14, 76, 106, 97, 118, 97, 47, 110, 101, 116, 47, 85, 82, 76, 59, 1, 0, 3, 117, 99, 108, 1, 0, 25, 76, 106, 97, 118, 97, 47, 110, 101, 116, 47, 85, 82, 76, 67, 108, 97, 115, 115, 76, 111, 97, 100, 101, 114, 59, 1, 0, 6, 108, 111, 97, 100, 101, 114, 1, 0, 17, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 67, 108, 97, 115, 115, 59, 1, 0, 26, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 114, 101, 102, 108, 101, 99, 116, 47, 77, 101, 116, 104, 111, 100, 59, 1, 0, 22, 76, 111, 99, 97, 108, 86, 97, 114, 105, 97, 98, 108, 101, 84, 121, 112, 101, 84, 97, 98, 108, 101, 1, 0, 20, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 67, 108, 97, 115, 115, 60, 42, 62, 59, 10, 0, 69, 0, 71, 7, 0, 70, 1, 0, 40, 110, 101, 116, 47, 109, 105, 110, 101, 99, 114, 97, 102, 116, 102, 111, 114, 103, 101, 47, 102, 109, 108, 47, 108, 111, 97, 100, 105, 110, 103, 47, 70, 77, 76, 76, 111, 97, 100, 101, 114, 12, 0, 72, 0, 73, 1, 0, 18, 103, 101, 116, 67, 111, 114, 101, 77, 111, 100, 80, 114, 111, 118, 105, 100, 101, 114, 1, 0, 56, 40, 41, 76, 110, 101, 116, 47, 109, 105, 110, 101, 99, 114, 97, 102, 116, 102, 111, 114, 103, 101, 47, 102, 111, 114, 103, 101, 115, 112, 105, 47, 99, 111, 114, 101, 109, 111, 100, 47, 73, 67, 111, 114, 101, 77, 111, 100, 80, 114, 111, 118, 105, 100, 101, 114, 59, 7, 0, 75, 1, 0, 42, 110, 101, 116, 47, 109, 105, 110, 101, 99, 114, 97, 102, 116, 102, 111, 114, 103, 101, 47, 99, 111, 114, 101, 109, 111, 100, 47, 67, 111, 114, 101, 77, 111, 100, 80, 114, 111, 118, 105, 100, 101, 114, 8, 0, 77, 1, 0, 6, 101, 110, 103, 105, 110, 101, 10, 0, 1, 0, 79, 12, 0, 80, 0, 81, 1, 0, 9, 114, 101, 97, 100, 70, 105, 101, 108, 100, 1, 0, 73, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 67, 108, 97, 115, 115, 59, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 7, 0, 83, 1, 0, 40, 110, 101, 116, 47, 109, 105, 110, 101, 99, 114, 97, 102, 116, 102, 111, 114, 103, 101, 47, 99, 111, 114, 101, 109, 111, 100, 47, 67, 111, 114, 101, 77, 111, 100, 69, 110, 103, 105, 110, 101, 8, 0, 85, 1, 0, 8, 99, 111, 114, 101, 77, 111, 100, 115, 7, 0, 87, 1, 0, 14, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 76, 105, 115, 116, 11, 0, 86, 0, 89, 12, 0, 90, 0, 91, 1, 0, 8, 105, 116, 101, 114, 97, 116, 111, 114, 1, 0, 22, 40, 41, 76, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 73, 116, 101, 114, 97, 116, 111, 114, 59, 11, 0, 93, 0, 95, 7, 0, 94, 1, 0, 18, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 73, 116, 101, 114, 97, 116, 111, 114, 12, 0, 96, 0, 97, 1, 0, 4, 110, 101, 120, 116, 1, 0, 20, 40, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 7, 0, 99, 1, 0, 34, 110, 101, 116, 47, 109, 105, 110, 101, 99, 114, 97, 102, 116, 102, 111, 114, 103, 101, 47, 99, 111, 114, 101, 109, 111, 100, 47, 67, 111, 114, 101, 77, 111, 100, 8, 0, 101, 1, 0, 4, 102, 105, 108, 101, 7, 0, 103, 1, 0, 55, 110, 101, 116, 47, 109, 105, 110, 101, 99, 114, 97, 102, 116, 102, 111, 114, 103, 101, 47, 102, 109, 108, 47, 108, 111, 97, 100, 105, 110, 103, 47, 109, 111, 100, 100, 105, 115, 99, 111, 118, 101, 114, 121, 47, 67, 111, 114, 101, 77, 111, 100, 70, 105, 108, 101, 10, 0, 102, 0, 105, 12, 0, 106, 0, 107, 1, 0, 7, 103, 101, 116, 80, 97, 116, 104, 1, 0, 22, 40, 41, 76, 106, 97, 118, 97, 47, 110, 105, 111, 47, 102, 105, 108, 101, 47, 80, 97, 116, 104, 59, 11, 0, 109, 0, 111, 7, 0, 110, 1, 0, 18, 106, 97, 118, 97, 47, 110, 105, 111, 47, 102, 105, 108, 101, 47, 80, 97, 116, 104, 12, 0, 112, 0, 14, 1, 0, 6, 116, 111, 70, 105, 108, 101, 10, 0, 16, 0, 114, 12, 0, 115, 0, 116, 1, 0, 6, 105, 115, 70, 105, 108, 101, 1, 0, 3, 40, 41, 90, 10, 0, 16, 0, 118, 12, 0, 119, 0, 120, 1, 0, 7, 103, 101, 116, 78, 97, 109, 101, 1, 0, 20, 40, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 8, 0, 122, 1, 0, 4, 46, 106, 97, 114, 10, 0, 124, 0, 126, 7, 0, 125, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 12, 0, 127, 0, 128, 1, 0, 8, 101, 110, 100, 115, 87, 105, 116, 104, 1, 0, 21, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 90, 7, 0, 130, 1, 0, 21, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 106, 97, 114, 47, 74, 97, 114, 70, 105, 108, 101, 10, 0, 129, 0, 132, 12, 0, 33, 0, 133, 1, 0, 17, 40, 76, 106, 97, 118, 97, 47, 105, 111, 47, 70, 105, 108, 101, 59, 41, 86, 7, 0, 135, 1, 0, 23, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 66, 117, 105, 108, 100, 101, 114, 10, 0, 38, 0, 118, 10, 0, 124, 0, 138, 12, 0, 139, 0, 140, 1, 0, 7, 114, 101, 112, 108, 97, 99, 101, 1, 0, 22, 40, 67, 67, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 10, 0, 124, 0, 142, 12, 0, 143, 0, 144, 1, 0, 7, 118, 97, 108, 117, 101, 79, 102, 1, 0, 38, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 10, 0, 134, 0, 146, 12, 0, 33, 0, 147, 1, 0, 21, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 86, 8, 0, 149, 1, 0, 6, 46, 99, 108, 97, 115, 115, 10, 0, 134, 0, 151, 12, 0, 152, 0, 153, 1, 0, 6, 97, 112, 112, 101, 110, 100, 1, 0, 45, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 66, 117, 105, 108, 100, 101, 114, 59, 10, 0, 134, 0, 155, 12, 0, 156, 0, 120, 1, 0, 8, 116, 111, 83, 116, 114, 105, 110, 103, 10, 0, 129, 0, 158, 12, 0, 159, 0, 160, 1, 0, 11, 103, 101, 116, 74, 97, 114, 69, 110, 116, 114, 121, 1, 0, 44, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 76, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 106, 97, 114, 47, 74, 97, 114, 69, 110, 116, 114, 121, 59, 10, 0, 129, 0, 162, 12, 0, 163, 0, 6, 1, 0, 5, 99, 108, 111, 115, 101, 10, 0, 165, 0, 167, 7, 0, 166, 1, 0, 19, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 84, 104, 114, 111, 119, 97, 98, 108, 101, 12, 0, 168, 0, 169, 1, 0, 13, 97, 100, 100, 83, 117, 112, 112, 114, 101, 115, 115, 101, 100, 1, 0, 24, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 84, 104, 114, 111, 119, 97, 98, 108, 101, 59, 41, 86, 7, 0, 171, 1, 0, 23, 106, 97, 118, 97, 47, 105, 111, 47, 70, 105, 108, 101, 73, 110, 112, 117, 116, 83, 116, 114, 101, 97, 109, 10, 0, 170, 0, 132, 10, 0, 16, 0, 174, 12, 0, 175, 0, 176, 1, 0, 6, 108, 101, 110, 103, 116, 104, 1, 0, 3, 40, 41, 74, 10, 0, 178, 0, 180, 7, 0, 179, 1, 0, 32, 99, 111, 109, 47, 103, 111, 111, 103, 108, 101, 47, 99, 111, 109, 109, 111, 110, 47, 105, 111, 47, 66, 121, 116, 101, 83, 116, 114, 101, 97, 109, 115, 12, 0, 181, 0, 182, 1, 0, 9, 114, 101, 97, 100, 70, 117, 108, 108, 121, 1, 0, 26, 40, 76, 106, 97, 118, 97, 47, 105, 111, 47, 73, 110, 112, 117, 116, 83, 116, 114, 101, 97, 109, 59, 91, 66, 41, 86, 7, 0, 184, 1, 0, 32, 111, 114, 103, 47, 111, 98, 106, 101, 99, 116, 119, 101, 98, 47, 97, 115, 109, 47, 116, 114, 101, 101, 47, 67, 108, 97, 115, 115, 78, 111, 100, 101, 10, 0, 183, 0, 186, 12, 0, 33, 0, 6, 7, 0, 188, 1, 0, 29, 111, 114, 103, 47, 111, 98, 106, 101, 99, 116, 119, 101, 98, 47, 97, 115, 109, 47, 67, 108, 97, 115, 115, 82, 101, 97, 100, 101, 114, 10, 0, 187, 0, 190, 12, 0, 33, 0, 191, 1, 0, 5, 40, 91, 66, 41, 86, 10, 0, 187, 0, 193, 12, 0, 194, 0, 195, 1, 0, 6, 97, 99, 99, 101, 112, 116, 1, 0, 36, 40, 76, 111, 114, 103, 47, 111, 98, 106, 101, 99, 116, 119, 101, 98, 47, 97, 115, 109, 47, 67, 108, 97, 115, 115, 86, 105, 115, 105, 116, 111, 114, 59, 73, 41, 86, 9, 0, 183, 0, 197, 12, 0, 198, 0, 199, 1, 0, 4, 110, 97, 109, 101, 1, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 10, 0, 124, 0, 201, 12, 0, 202, 0, 203, 1, 0, 6, 101, 113, 117, 97, 108, 115, 1, 0, 21, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 41, 90, 10, 0, 170, 0, 162, 11, 0, 93, 0, 206, 12, 0, 207, 0, 116, 1, 0, 7, 104, 97, 115, 78, 101, 120, 116, 7, 0, 209, 1, 0, 31, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 73, 108, 108, 101, 103, 97, 108, 83, 116, 97, 116, 101, 69, 120, 99, 101, 112, 116, 105, 111, 110, 8, 0, 211, 1, 0, 50, 68, 101, 115, 99, 114, 105, 112, 116, 32, 115, 111, 109, 101, 104, 111, 119, 32, 108, 111, 97, 100, 101, 100, 32, 119, 105, 116, 104, 111, 117, 116, 32, 98, 101, 105, 110, 103, 32, 97, 32, 99, 111, 114, 101, 109, 111, 100, 46, 46, 46, 10, 0, 208, 0, 146, 1, 0, 8, 112, 114, 111, 118, 105, 100, 101, 114, 1, 0, 44, 76, 110, 101, 116, 47, 109, 105, 110, 101, 99, 114, 97, 102, 116, 102, 111, 114, 103, 101, 47, 99, 111, 114, 101, 109, 111, 100, 47, 67, 111, 114, 101, 77, 111, 100, 80, 114, 111, 118, 105, 100, 101, 114, 59, 1, 0, 42, 76, 110, 101, 116, 47, 109, 105, 110, 101, 99, 114, 97, 102, 116, 102, 111, 114, 103, 101, 47, 99, 111, 114, 101, 109, 111, 100, 47, 67, 111, 114, 101, 77, 111, 100, 69, 110, 103, 105, 110, 101, 59, 1, 0, 16, 76, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 76, 105, 115, 116, 59, 1, 0, 11, 102, 105, 108, 101, 87, 114, 97, 112, 112, 101, 114, 1, 0, 57, 76, 110, 101, 116, 47, 109, 105, 110, 101, 99, 114, 97, 102, 116, 102, 111, 114, 103, 101, 47, 102, 109, 108, 47, 108, 111, 97, 100, 105, 110, 103, 47, 109, 111, 100, 100, 105, 115, 99, 111, 118, 101, 114, 121, 47, 67, 111, 114, 101, 77, 111, 100, 70, 105, 108, 101, 59, 1, 0, 5, 101, 110, 116, 114, 121, 1, 0, 24, 76, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 106, 97, 114, 47, 74, 97, 114, 69, 110, 116, 114, 121, 59, 1, 0, 5, 98, 121, 116, 101, 115, 1, 0, 2, 91, 66, 1, 0, 5, 99, 108, 97, 122, 122, 1, 0, 34, 76, 111, 114, 103, 47, 111, 98, 106, 101, 99, 116, 119, 101, 98, 47, 97, 115, 109, 47, 116, 114, 101, 101, 47, 67, 108, 97, 115, 115, 78, 111, 100, 101, 59, 1, 0, 7, 99, 111, 114, 101, 77, 111, 100, 1, 0, 36, 76, 110, 101, 116, 47, 109, 105, 110, 101, 99, 114, 97, 102, 116, 102, 111, 114, 103, 101, 47, 99, 111, 114, 101, 109, 111, 100, 47, 67, 111, 114, 101, 77, 111, 100, 59, 1, 0, 3, 106, 97, 114, 1, 0, 23, 76, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 106, 97, 114, 47, 74, 97, 114, 70, 105, 108, 101, 59, 1, 0, 2, 105, 110, 1, 0, 25, 76, 106, 97, 118, 97, 47, 105, 111, 47, 70, 105, 108, 101, 73, 110, 112, 117, 116, 83, 116, 114, 101, 97, 109, 59, 1, 0, 54, 76, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 76, 105, 115, 116, 60, 76, 110, 101, 116, 47, 109, 105, 110, 101, 99, 114, 97, 102, 116, 102, 111, 114, 103, 101, 47, 99, 111, 114, 101, 109, 111, 100, 47, 67, 111, 114, 101, 77, 111, 100, 59, 62, 59, 1, 0, 13, 83, 116, 97, 99, 107, 77, 97, 112, 84, 97, 98, 108, 101, 7, 0, 234, 1, 0, 22, 106, 97, 118, 97, 47, 117, 116, 105, 108, 47, 106, 97, 114, 47, 74, 97, 114, 69, 110, 116, 114, 121, 7, 0, 222, 1, 0, 9, 83, 105, 103, 110, 97, 116, 117, 114, 101, 1, 0, 83, 60, 84, 58, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 62, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 67, 108, 97, 115, 115, 60, 42, 62, 59, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 41, 84, 84, 59, 1, 0, 34, 82, 117, 110, 116, 105, 109, 101, 86, 105, 115, 105, 98, 108, 101, 80, 97, 114, 97, 109, 101, 116, 101, 114, 65, 110, 110, 111, 116, 97, 116, 105, 111, 110, 115, 1, 0, 27, 76, 106, 97, 118, 97, 120, 47, 97, 110, 110, 111, 116, 97, 116, 105, 111, 110, 47, 78, 117, 108, 108, 97, 98, 108, 101, 59, 10, 0, 1, 0, 241, 12, 0, 242, 0, 243, 1, 0, 16, 103, 101, 116, 68, 101, 99, 108, 97, 114, 101, 100, 70, 105, 101, 108, 100, 1, 0, 62, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 67, 108, 97, 115, 115, 59, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 114, 101, 102, 108, 101, 99, 116, 47, 70, 105, 101, 108, 100, 59, 10, 0, 245, 0, 247, 7, 0, 246, 1, 0, 23, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 114, 101, 102, 108, 101, 99, 116, 47, 70, 105, 101, 108, 100, 12, 0, 248, 0, 249, 1, 0, 3, 103, 101, 116, 1, 0, 38, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 10, 0, 251, 0, 253, 7, 0, 252, 1, 0, 13, 108, 111, 109, 98, 111, 107, 47, 76, 111, 109, 98, 111, 107, 12, 0, 254, 0, 255, 1, 0, 11, 115, 110, 101, 97, 107, 121, 84, 104, 114, 111, 119, 1, 0, 51, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 84, 104, 114, 111, 119, 97, 98, 108, 101, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 82, 117, 110, 116, 105, 109, 101, 69, 120, 99, 101, 112, 116, 105, 111, 110, 59, 1, 0, 5, 111, 119, 110, 101, 114, 1, 0, 8, 105, 110, 115, 116, 97, 110, 99, 101, 1, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 59, 1, 0, 5, 102, 105, 101, 108, 100, 1, 0, 25, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 114, 101, 102, 108, 101, 99, 116, 47, 70, 105, 101, 108, 100, 59, 1, 0, 3, 36, 101, 120, 1, 0, 21, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 84, 104, 114, 111, 119, 97, 98, 108, 101, 59, 1, 0, 65, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 67, 108, 97, 115, 115, 60, 42, 62, 59, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 114, 101, 102, 108, 101, 99, 116, 47, 70, 105, 101, 108, 100, 59, 10, 0, 38, 1, 9, 12, 0, 242, 1, 10, 1, 0, 45, 40, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 41, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 114, 101, 102, 108, 101, 99, 116, 47, 70, 105, 101, 108, 100, 59, 10, 0, 245, 1, 12, 12, 1, 13, 1, 14, 1, 0, 13, 115, 101, 116, 65, 99, 99, 101, 115, 115, 105, 98, 108, 101, 1, 0, 4, 40, 90, 41, 86, 10, 0, 3, 0, 186, 7, 1, 17, 1, 0, 39, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 85, 110, 115, 117, 112, 112, 111, 114, 116, 101, 100, 79, 112, 101, 114, 97, 116, 105, 111, 110, 69, 120, 99, 101, 112, 116, 105, 111, 110, 8, 1, 19, 1, 0, 50, 84, 104, 105, 115, 32, 105, 115, 32, 97, 32, 117, 116, 105, 108, 105, 116, 121, 32, 99, 108, 97, 115, 115, 32, 97, 110, 100, 32, 99, 97, 110, 110, 111, 116, 32, 98, 101, 32, 105, 110, 115, 116, 97, 110, 116, 105, 97, 116, 101, 100, 10, 1, 16, 0, 146, 1, 0, 4, 116, 104, 105, 115, 1, 0, 40, 76, 110, 101, 116, 47, 116, 104, 101, 111, 112, 97, 108, 103, 97, 109, 101, 115, 47, 100, 101, 115, 99, 114, 105, 112, 116, 47, 68, 101, 115, 99, 114, 105, 112, 116, 73, 110, 105, 116, 59, 1, 0, 10, 83, 111, 117, 114, 99, 101, 70, 105, 108, 101, 1, 0, 17, 68, 101, 115, 99, 114, 105, 112, 116, 73, 110, 105, 116, 46, 106, 97, 118, 97, 0, 49, 0, 1, 0, 3, 0, 0, 0, 0, 0, 5, 0, 9, 0, 5, 0, 6, 0, 2, 0, 10, 0, 0, 0, 184, 0, 6, 0, 5, 0, 0, 0, 60, 184, 0, 11, 75, 42, 182, 0, 15, 182, 0, 21, 76, 187, 0, 27, 89, 4, 189, 0, 29, 89, 3, 43, 83, 183, 0, 31, 77, 18, 35, 4, 44, 184, 0, 37, 78, 45, 18, 43, 3, 189, 0, 38, 182, 0, 45, 58, 4, 25, 4, 1, 3, 189, 0, 3, 182, 0, 49, 87, 177, 0, 0, 0, 3, 0, 55, 0, 0, 0, 30, 0, 7, 0, 0, 0, 33, 0, 4, 0, 34, 0, 12, 0, 36, 0, 28, 0, 37, 0, 36, 0, 39, 0, 48, 0, 40, 0, 59, 0, 41, 0, 56, 0, 0, 0, 52, 0, 5, 0, 4, 0, 56, 0, 57, 0, 58, 0, 0, 0, 12, 0, 48, 0, 59, 0, 60, 0, 1, 0, 28, 0, 32, 0, 61, 0, 62, 0, 2, 0, 36, 0, 24, 0, 63, 0, 64, 0, 3, 0, 48, 0, 12, 0, 44, 0, 65, 0, 4, 0, 66, 0, 0, 0, 12, 0, 1, 0, 36, 0, 24, 0, 63, 0, 67, 0, 3, 0, 7, 0, 0, 0, 4, 0, 1, 0, 8, 0, 10, 0, 13, 0, 14, 0, 2, 0, 10, 0, 0, 4, 0, 0, 6, 0, 13, 0, 0, 1, 172, 184, 0, 68, 192, 0, 74, 75, 18, 74, 18, 76, 42, 184, 0, 78, 192, 0, 82, 76, 18, 82, 18, 84, 43, 184, 0, 78, 192, 0, 86, 77, 1, 58, 4, 44, 185, 0, 88, 1, 0, 58, 9, 167, 1, 102, 25, 9, 185, 0, 92, 1, 0, 192, 0, 98, 58, 8, 18, 98, 18, 100, 25, 8, 184, 0, 78, 192, 0, 102, 78, 45, 182, 0, 104, 185, 0, 108, 1, 0, 58, 4, 25, 4, 182, 0, 113, 153, 1, 58, 25, 4, 182, 0, 117, 18, 121, 182, 0, 123, 153, 0, 134, 1, 58, 10, 1, 58, 11, 187, 0, 129, 89, 25, 4, 183, 0, 131, 58, 12, 25, 12, 187, 0, 134, 89, 18, 1, 182, 0, 136, 16, 46, 16, 47, 182, 0, 137, 184, 0, 141, 183, 0, 145, 18, 148, 182, 0, 150, 182, 0, 154, 182, 0, 157, 58, 5, 25, 5, 198, 0, 16, 25, 12, 198, 0, 247, 25, 12, 182, 0, 161, 167, 0, 239, 25, 12, 198, 0, 224, 25, 12, 182, 0, 161, 167, 0, 216, 58, 10, 25, 12, 198, 0, 8, 25, 12, 182, 0, 161, 25, 10, 191, 58, 11, 25, 10, 199, 0, 10, 25, 11, 58, 10, 167, 0, 17, 25, 10, 25, 11, 165, 0, 10, 25, 10, 25, 11, 182, 0, 164, 25, 10, 191, 25, 4, 182, 0, 117, 18, 148, 182, 0, 123, 153, 0, 157, 1, 58, 10, 1, 58, 11, 187, 0, 170, 89, 25, 4, 183, 0, 172, 58, 12, 25, 4, 182, 0, 173, 136, 188, 8, 58, 6, 25, 12, 25, 6, 184, 0, 177, 187, 0, 183, 89, 183, 0, 185, 58, 7, 187, 0, 187, 89, 25, 6, 183, 0, 189, 25, 7, 16, 7, 182, 0, 192, 25, 7, 180, 0, 196, 18, 1, 182, 0, 136, 16, 46, 16, 47, 182, 0, 137, 182, 0, 200, 153, 0, 16, 25, 12, 198, 0, 80, 25, 12, 182, 0, 204, 167, 0, 72, 25, 12, 198, 0, 57, 25, 12, 182, 0, 204, 167, 0, 49, 58, 10, 25, 12, 198, 0, 8, 25, 12, 182, 0, 204, 25, 10, 191, 58, 11, 25, 10, 199, 0, 10, 25, 11, 58, 10, 167, 0, 17, 25, 10, 25, 11, 165, 0, 10, 25, 10, 25, 11, 182, 0, 164, 25, 10, 191, 25, 9, 185, 0, 205, 1, 0, 154, 254, 150, 25, 4, 199, 0, 13, 187, 0, 208, 89, 18, 210, 183, 0, 212, 191, 25, 4, 176, 0, 6, 0, 119, 0, 161, 0, 187, 0, 0, 0, 108, 0, 171, 0, 202, 0, 0, 0, 174, 0, 202, 0, 202, 0, 0, 1, 7, 1, 72, 1, 98, 0, 0, 0, 252, 1, 82, 1, 113, 0, 0, 1, 85, 1, 113, 1, 113, 0, 0, 0, 4, 0, 232, 0, 0, 1, 14, 0, 17, 255, 0, 45, 0, 10, 7, 0, 74, 7, 0, 82, 7, 0, 86, 0, 7, 0, 16, 0, 0, 0, 0, 7, 0, 93, 0, 0, 255, 0, 128, 0, 13, 7, 0, 74, 7, 0, 82, 7, 0, 86, 7, 0, 102, 7, 0, 16, 7, 0, 233, 0, 0, 7, 0, 98, 7, 0, 93, 7, 0, 165, 7, 0, 165, 7, 0, 129, 0, 0, 255, 0, 12, 0, 13, 7, 0, 74, 7, 0, 82, 7, 0, 86, 7, 0, 102, 7, 0, 16, 0, 0, 0, 7, 0, 98, 7, 0, 93, 7, 0, 165, 7, 0, 165, 7, 0, 129, 0, 1, 7, 0, 165, 250, 0, 11, 66, 7, 0, 165, 13, 13, 249, 0, 2, 255, 0, 107, 0, 13, 7, 0, 74, 7, 0, 82, 7, 0, 86, 7, 0, 102, 7, 0, 16, 0, 7, 0, 235, 7, 0, 183, 7, 0, 98, 7, 0, 93, 7, 0, 165, 7, 0, 165, 7, 0, 170, 0, 0, 255, 0, 12, 0, 13, 7, 0, 74, 7, 0, 82, 7, 0, 86, 7, 0, 102, 7, 0, 16, 0, 0, 0, 7, 0, 98, 7, 0, 93, 7, 0, 165, 7, 0, 165, 7, 0, 170, 0, 1, 7, 0, 165, 250, 0, 11, 66, 7, 0, 165, 13, 13, 255, 0, 2, 0, 10, 7, 0, 74, 7, 0, 82, 7, 0, 86, 0, 7, 0, 16, 0, 0, 0, 0, 7, 0, 93, 0, 0, 255, 0, 9, 0, 5, 7, 0, 74, 7, 0, 82, 7, 0, 86, 0, 7, 0, 16, 0, 0, 14, 0, 55, 0, 0, 0, 118, 0, 29, 0, 0, 0, 44, 0, 7, 0, 45, 0, 19, 0, 47, 0, 31, 0, 49, 0, 34, 0, 54, 0, 57, 0, 55, 0, 70, 0, 56, 0, 81, 0, 58, 0, 89, 0, 59, 0, 102, 0, 60, 0, 119, 0, 61, 0, 156, 0, 62, 0, 161, 0, 64, 0, 171, 0, 63, 0, 174, 0, 64, 0, 233, 0, 65, 0, 246, 0, 66, 1, 7, 0, 67, 1, 17, 0, 68, 1, 24, 0, 70, 1, 33, 0, 71, 1, 49, 0, 73, 1, 72, 0, 75, 1, 82, 0, 74, 1, 85, 0, 75, 1, 144, 0, 54, 1, 154, 0, 80, 1, 159, 0, 81, 1, 169, 0, 83, 0, 56, 0, 0, 0, 112, 0, 11, 0, 7, 1, 165, 0, 213, 0, 214, 0, 0, 0, 19, 1, 153, 0, 77, 0, 215, 0, 1, 0, 31, 1, 141, 0, 85, 0, 216, 0, 2, 0, 70, 1, 74, 0, 217, 0, 218, 0, 3, 0, 34, 1, 138, 0, 101, 0, 58, 0, 4, 0, 156, 0, 31, 0, 219, 0, 220, 0, 5, 1, 17, 0, 81, 0, 221, 0, 222, 0, 6, 1, 33, 0, 65, 0, 223, 0, 224, 0, 7, 0, 57, 1, 87, 0, 225, 0, 226, 0, 8, 0, 119, 0, 80, 0, 227, 0, 228, 0, 12, 1, 7, 0, 103, 0, 229, 0, 230, 0, 12, 0, 66, 0, 0, 0, 12, 0, 1, 0, 31, 1, 141, 0, 85, 0, 231, 0, 2, 0, 7, 0, 0, 0, 4, 0, 1, 0, 8, 0, 10, 0, 80, 0, 81, 0, 3, 0, 10, 0, 0, 0, 147, 0, 2, 0, 4, 0, 0, 0, 15, 42, 43, 184, 0, 240, 78, 45, 44, 182, 0, 244, 176, 78, 45, 191, 0, 1, 0, 0, 0, 11, 0, 12, 0, 165, 0, 4, 0, 232, 0, 0, 0, 6, 0, 1, 76, 7, 0, 165, 0, 55, 0, 0, 0, 18, 0, 4, 0, 0, 0, 91, 0, 6, 0, 92, 0, 12, 0, 93, 0, 14, 0, 90, 0, 56, 0, 0, 0, 52, 0, 5, 0, 0, 0, 15, 1, 0, 0, 64, 0, 0, 0, 0, 0, 15, 0, 198, 0, 199, 0, 1, 0, 0, 0, 15, 1, 1, 1, 2, 0, 2, 0, 6, 0, 6, 1, 3, 1, 4, 0, 3, 0, 13, 0, 2, 1, 5, 1, 6, 0, 3, 0, 66, 0, 0, 0, 12, 0, 1, 0, 0, 0, 15, 1, 0, 0, 67, 0, 0, 0, 236, 0, 0, 0, 2, 0, 237, 0, 238, 0, 0, 0, 11, 3, 0, 0, 0, 0, 0, 1, 0, 239, 0, 0, 0, 10, 0, 242, 0, 243, 0, 3, 0, 10, 0, 0, 0, 101, 0, 2, 0, 3, 0, 0, 0, 13, 42, 43, 182, 1, 8, 77, 44, 4, 182, 1, 11, 44, 176, 0, 0, 0, 3, 0, 55, 0, 0, 0, 14, 0, 3, 0, 0, 0, 96, 0, 6, 0, 97, 0, 11, 0, 98, 0, 56, 0, 0, 0, 32, 0, 3, 0, 0, 0, 13, 1, 0, 0, 64, 0, 0, 0, 0, 0, 13, 0, 198, 0, 199, 0, 1, 0, 6, 0, 7, 1, 3, 1, 4, 0, 2, 0, 66, 0, 0, 0, 12, 0, 1, 0, 0, 0, 13, 1, 0, 0, 67, 0, 0, 0, 7, 0, 0, 0, 4, 0, 1, 0, 8, 0, 236, 0, 0, 0, 2, 1, 7, 0, 2, 0, 33, 0, 6, 0, 1, 0, 10, 0, 0, 0, 57, 0, 3, 0, 1, 0, 0, 0, 15, 42, 183, 1, 15, 187, 1, 16, 89, 19, 1, 18, 183, 1, 20, 191, 0, 0, 0, 2, 0, 55, 0, 0, 0, 6, 0, 1, 0, 0, 0, 30, 0, 56, 0, 0, 0, 12, 0, 1, 0, 0, 0, 15, 1, 21, 1, 22, 0, 0, 0, 1, 1, 23, 0, 0, 0, 2, 1, 24];

var run = false;

function initializeCoreMod() {
	print("Loading Descript...");
	
	return {
		descript_core: {
			target: {
				type: "CLASS",
				names: listMainClasses // sorry, no other main classes right now
			},
			transformer: transformIt
		}
	};
}

function listMainClasses() {
	return MAIN_CLASSES;
}

function transformIt(clazz) {
        if (run)
            return clazz;
	    
        run = true;
        
        print("Injecting Descript call...");
        
        var Opcodes = Java.type("org.objectweb.asm.Opcodes");
        var MethodNode = Java.type("org.objectweb.asm.tree.MethodNode");
        var LdcInsnNode = Java.type("org.objectweb.asm.tree.LdcInsnNode");
        var IntInsnNode = Java.type("org.objectweb.asm.tree.IntInsnNode");
        var VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
        var InsnNode = Java.type("org.objectweb.asm.tree.InsnNode");
        var LabelNode = Java.type("org.objectweb.asm.tree.LabelNode");
        var TypeInsnNode = Java.type("org.objectweb.asm.tree.TypeInsnNode");
        var MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
        var LineNumberNode = Java.type("org.objectweb.asm.tree.LineNumberNode");
        var FieldInsnNode = Java.type("org.objectweb.asm.tree.FieldInsnNode");
        
        var clinit = clazz.methods.stream().filter(staticInitFilter).findAny().orElse(null);
        
        if (clinit == null) {
                clinit = new MethodNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_SYNTHETIC, "<clinit>", "()V", null, null);
                clazz.methods.add(clinit);
        }
        
        // 9213: byte[] bytes = new byte[LENGTH];
        var lbl = new LabelNode();
		var topInsn = new LineNumberNode(9213, lbl);
		clinit.instructions.insert(topInsn);
		topInsn = pushInstruction(clinit, topInsn, lbl);
        
        topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode(INIT_BYTECODE.length));
        topInsn = pushInstruction(clinit, topInsn, new IntInsnNode(Opcodes.NEWARRAY, Opcodes.T_BYTE));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 1));
        
        // 9214
        topInsn = addLineNumber(clinit, topInsn, 9214);
        
        for (var i = 0; i < INIT_BYTECODE.length; i++) {
                // bytes[i] = BYTECODE[i];
                topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 1));
                topInsn = pushInstruction(clinit, topInsn, new IntInsnNode(Opcodes.SIPUSH, i));
                topInsn = pushInstruction(clinit, topInsn, new IntInsnNode(Opcodes.BIPUSH, INIT_BYTECODE[i]));
                topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.BASTORE));
        }
        
        // 9215: URLClassLoader classLoader = new URLClassLoader(new URL[0]);
        topInsn = addLineNumber(clinit, topInsn, 9215);
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.NEW, "java/net/URLClassLoader"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.DUP));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.ANEWARRAY, "java/net/URL"));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/net/URLClassLoader", "<init>", "([Ljava/net/URL;)V"));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 2));
        
        // 9216: Class[] params = new Class[4];
        topInsn = addLineNumber(clinit, topInsn, 9216);
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_4));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Class"));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 3));
        
        // 9217: params[0] = Class.forName("java.lang.String");
        topInsn = addLineNumber(clinit, topInsn, 9217);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 3));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
        topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode("java.lang.String")); // Have to do it this way because JS doesn't let you access ClassLoaders
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9218: params[1] = bytes.getClass();
        topInsn = addLineNumber(clinit, topInsn, 9218);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 3));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_1));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 1));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9219: params[2] = Integer.TYPE;
        topInsn = addLineNumber(clinit, topInsn, 9219);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 3));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_2));
        topInsn = pushInstruction(clinit, topInsn, new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/Integer", "TYPE", "Ljava/lang/Class;"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9220: params[3] = Integer.TYPE;
        topInsn = addLineNumber(clinit, topInsn, 9220);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 3));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_3));
        topInsn = pushInstruction(clinit, topInsn, new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/Integer", "TYPE", "Ljava/lang/Class;"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9221: Method defineClass = Class.forName("java.lang.ClassLoader").getDeclaredMethod("defineClass", params);
        topInsn = addLineNumber(clinit, topInsn, 9221);
        topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode("java.lang.ClassLoader"));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;"));
        topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode("defineClass"));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 3));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;"));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 4));
        
        // 9222: defineClass.setAccessible(true);
        topInsn = addLineNumber(clinit, topInsn, 9222);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 4));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_1));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/reflect/Method", "setAccessible", "(Z)V"));
        
        // 9223: Object[] args = new Object[4];
        topInsn = addLineNumber(clinit, topInsn, 9223);
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_4));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 5));
        
        // 9224: args[0] = "net.theopalgames.descript.DescriptInit";
        topInsn = addLineNumber(clinit, topInsn, 9224);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 5));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
        topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode("net.theopalgames.descript.DescriptInit"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9225: args[1] = bytes;
        topInsn = addLineNumber(clinit, topInsn, 9225);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 5));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_1));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 1));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9226: args[2] = new Integer(0);
        topInsn = addLineNumber(clinit, topInsn, 9226);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 5));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_2));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.NEW, "java/lang/Integer"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.DUP));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/Integer", "<init>", "(I)V"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9227: args[3] = bytes.length;
        topInsn = addLineNumber(clinit, topInsn, 9227);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 5));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_3));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.NEW, "java/lang/Integer"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.DUP));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 1));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ARRAYLENGTH));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/Integer", "<init>", "(I)V"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9228: Class<?> clazz = (Class<?>) defineClass.invoke(classLoader, args);
        topInsn = addLineNumber(clinit, topInsn, 9228);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 4));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 2));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 5)); // yay pi (not anymore)
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/reflect/Method", "invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;"));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Class"));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 6));
        
        // clazz.getDeclaredMethod("javaEntry", new Class[0]).invoke(null, new Object[0]);
        topInsn = addLineNumber(clinit, topInsn, 9229);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 6));
        topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode("javaEntry"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Class"));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ACONST_NULL));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/reflect/Method", "invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.POP));
        
        // return;
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.RETURN));
        
        return clazz;
}

function staticInitFilter(method) {
	return method.name == "<clinit>";
}

function pushInstruction(method, topInsn, insn) {
	method.instructions.insert(topInsn, insn);
	return insn;
}

function addLineNumber(method, topInsn, number) {
	var LineNumberNode = Java.type("org.objectweb.asm.tree.LineNumberNode");
    var LabelNode = Java.type("org.objectweb.asm.tree.LabelNode");
	
	var lbl = new LabelNode();
	topInsn = pushInstruction(method, topInsn, new LineNumberNode(number, lbl));
	topInsn = pushInstruction(method, topInsn, lbl);
	
	return lbl;
}
