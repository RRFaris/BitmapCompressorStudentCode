/******************************************************************************
 *  Compilation:  javac BitmapCompressor.java
 *  Execution:    java BitmapCompressor - < input.bin   (compress)
 *  Execution:    java BitmapCompressor + < input.bin   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   q32x48.bin
 *                q64x96.bin
 *                mystery.bin
 *
 *  Compress or expand binary input from standard input.
 *
 *  % java DumpBinary 0 < mystery.bin
 *  8000 bits
 *
 *  % java BitmapCompressor - < mystery.bin | java DumpBinary 0
 *  1240 bits
 ******************************************************************************/

/**
 *  The {@code BitmapCompressor} class provides static methods for compressing
 *  and expanding a binary bitmap input.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 *  @author Ryan
 */
public class BitmapCompressor {

    public static final int BITS_PER_NUMBER = 5;
    public static final int BITS_PER_INT = 32;

    /**
     * Reads a sequence of bits from standard input, compresses them,
     * and writes the results to standard output.
     */
    public static void compress() {
        String str = BinaryStdIn.readString();
        int strLength = str.length();

        int count = 0;

        BinaryStdOut.write(strLength, BITS_PER_INT);

        for (int i = 0; i < strLength; i++) {
            while (str.charAt(i) != str.charAt(i+1)) {
                count++;
            }
            BinaryStdOut.write(count, BITS_PER_NUMBER);
            i += count;
        }

        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {
        int length = BinaryStdIn.readInt();

        for (int i = 0; i < length; i++) {
            int numRepetitions = BinaryStdIn.readInt(BITS_PER_NUMBER);
            for (int j = 0; i < numRepetitions; j++) {
                if (i % 2 == 0)
                    BinaryStdOut.write(0);
                else
                    BinaryStdOut.write(1);
            }
        }
        BinaryStdOut.close();
    }

    /**
     * When executed at the command-line, run {@code compress()} if the command-line
     * argument is "-" and {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}