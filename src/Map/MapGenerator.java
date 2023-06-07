package Map;

import java.util.Arrays;
import java.util.Collections;

public class MapGenerator {

    // you can set this value to true and programme will generate maps with four ghosts only

    private static final boolean usesFourGhost = false;

    private static Object[][] data;

    public static Object[][] getData() {
        return data;
    }

    protected static int[][] ghostsLocation;

    private static int startR;

    private static int startC;

    private static int w;

    private static int h;

    public static int[][] getLocation() {
        return ghostsLocation;
    }

    public static void lock() {
        for (int i = startR; i < startR + h; i++) {
            for (int j = startC; j < startC + w; j++) {
                if(i == startR || i == startR + h - 1 || j == startC || j == startC + w - 1) data[i][j] = "W";
                else data[i][j] = " ";
            }
        }
    }

    public static void unlock() {
        for (int i = startR; i < startR + h; i++) {
            for (int j = startC; j < startC + w; j++) {
                if(i == startR && j > startC && j < startC + w -1) data[i][j] = "G";
            }
        }
    }


    private static final Object[][][] blocks = {
            {
                    {"•", "•"},
                    {"•", "W"}
            },
            {
                    {"•", "•"},
                    {"•", "W"},
                    {"•", "W"}
            },
            {
                    {"•", "•", "•"},
                    {"•", "W", "W"}
            },
            {
                    {"•", "•", "•"},
                    {"•", "W", "W"},
                    {"•", "W", "W"}
            },
            {
                    {"•", "•", "•", "•"},
                    {"•", "•", "W", "•"},
                    {"•", "W", "W", "W"},
                    {"•", "•", "W", "•"},
            }
    };

    public static void generate(int numRows, int numCols) {

        // Creation of a box of size rows * columns

        data = new Object[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if(i == 0 || i == numRows - 1 || j == 0 || j == numCols - 1) {
                    data[i][j] = "W";
                }
                else data[i][j] = "•";
            }
        }

        // Creation of teleports in the middle of each side

        if(numRows % 2 == 0) {
            data[numRows / 2][0] = "•";
            data[numRows / 2 - 1][0] = "•";
            data[numRows / 2][numCols - 1] = "•";
            data[numRows / 2 - 1][numCols - 1] = "•";
        } else {
            data[numRows / 2][0] = "•";
            data[numRows / 2][numCols - 1] = "•";
        }

        if(numCols % 2 == 0) {
            data[0][numCols / 2] = "•";
            data[0][numCols / 2 - 1] = "•";
            data[numRows - 1][numCols / 2] = "•";
            data[numRows - 1][numCols / 2 - 1] = "•";
        } else {
            data[0][numCols / 2] = "•";
            data[numRows - 1][numCols / 2] = "•";
        }

        // Looking for area and number of ghosts, this information depends on map size

        h = 0;
        w = 0;


        int area = ((numCols * numRows) / 100 % 2) == 0 ? (numCols * numRows) / 100 : (numCols * numRows) / 100 + 1;

        if(usesFourGhost) area = 4;

        while (h == 0 || w == 0) {

            int height = numRows % 2 == 0 ? 2 : 1;

            int diff = 100;

            for (int i = height; i < numRows / 2; i += 2) {
                int width = area / i;
                if (width % 2 == numCols % 2) {
                    if (width * i == area) {
                        if (diff > Math.abs(width - i)) {
                            diff = Math.abs(width - i);
                            h = i;
                            w = width;
                        }
                    }
                }
            }

            area++;

        }

        // Animations.Ghost locations array stores information about ghosts coordinated in the cave

        ghostsLocation = new int[h * w][2];

        h += 2;
        w += 2;


        startR = numRows / 2 - (h / 2);
        startC = numCols / 2 - (w / 2);

        int itr = 0;

        for (int i = startR + 1; i < startR + h - 1; i++) {
            for (int j = startC + 1; j < startC + w - 1; j++) {
                ghostsLocation[itr][0] = i;
                ghostsLocation[itr++][1] = j;
            }
        }

        // Creation of a cave for ghosts

        lock();

        // Creating an array which is one-forth of the map

        int subH = numRows % 2 == 0 ? numRows / 2 - 1 : numRows / 2;
        int subW = numCols % 2 == 0 ? numCols / 2 - 1 : numCols / 2;

        Object[][] fourth = new Object[subH][subW];

        for (int i = 0; i < subH; i++) {
            for (int j = 0; j < subW; j++) {
                if(data[i][j].equals("W")) fourth[i][j] = data[i][j];
                else if(i == subH - 1 || j == subW - 1) fourth[i][j] = "•";
                else fourth[i][j] = "?";
            }
        }

        // Generating maze in the array

        for (int i = 0; i < subH; i++) {
            for (int j = 0; j < subW; j++) {
                if (fourth[i][j].equals("?")) {
                    Collections.shuffle(Arrays.asList(blocks));
                    boolean fits = true;
                    for(int z = 0; z < blocks.length && fits; z++) {
                        if(i + blocks[z].length <= subH && j + blocks[z][0].length <= subW) {
                            for (int y = i; y < blocks[z].length && fits; y++) {
                                for (int x = j; x < blocks[z][0].length; x++) {
                                    if (!fourth[y][x].equals("?")) {
                                        fits = false;
                                        break;
                                    }
                                }
                            }
                            if (fits) {
                                for (int y = 0; y < blocks[z].length; y++) {
                                    for (int x = 0; x < blocks[z][0].length; x++) {
                                        if(y + i < subH && x + j < subW) fourth[y + i][x + j] = blocks[z][y][x];
                                    }
                                }
                                z = blocks.length;
                            }
                        }
                    }
                }
            }
        }

        // Symmetrical copy of the array to other parts of the map

        for(int i = 0; i < 4; i++) {
            Object[][] mirror = new Object[subH][subW];
            int j = 0;
            int y = 0;
            switch (i) {
                case 1 -> {
                    y = numCols / 2 + 1;
                    for(int z = 0; z < subH; z++) {
                        for (int x = 0; x < subW; x++) {
                            mirror[z][x] = fourth[z][subW - x - 1];
                        }
                    }
                    for(int z = 0; z < subH; z++) {
                        System.arraycopy(mirror[z], 0, fourth[z], 0, subW);
                    }
                }
                case 2 -> {
                    j = numRows / 2 + 1;
                    for(int z = 0; z < subH; z++) {
                        for (int x = 0; x < subW; x++) {
                            mirror[z][x] = fourth[subH - z - 1][subW - x - 1];
                        }
                    }
                    for(int z = 0; z < subH; z++) {
                        System.arraycopy(mirror[z], 0, fourth[z], 0, subW);
                    }
                }
                case 3 -> {
                    j = numRows / 2 + 1;
                    y = numCols / 2 + 1;
                    for(int z = 0; z < subH; z++) {
                        for (int x = 0; x < subW; x++) {
                            mirror[z][x] = fourth[z][subW - x - 1];
                        }
                    }
                    for(int z = 0; z < subH; z++) {
                        System.arraycopy(mirror[z], 0, fourth[z], 0, subW);
                    }
                }
            }
            for (int m = j; m < subH + j; m++) {
                System.arraycopy(fourth[m - j], 0, data[m], y, subW);
            }
        }

        // Refreshing of the cave bounds

        lock();

        // Determine gray walls which are invisible for ghosts

        unlock();

    }

}
