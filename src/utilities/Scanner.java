package utilities;

import java.io.*;
import java.util.StringTokenizer;

//  input reader for efficient parsing
public class Scanner implements AutoCloseable  {
    private StringTokenizer st;
    private BufferedReader br;

    public Scanner(InputStream s) {
        br = new BufferedReader(new InputStreamReader(s));
    }

    // Constructor for file reading
    public Scanner(FileReader r) {
        br = new BufferedReader(r);
    }

    // Get next token
    public String next() throws IOException {
        while (st == null || !st.hasMoreTokens())
            st = new StringTokenizer(br.readLine());
        return st.nextToken();
    }

    // Read integer
    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    // Read long
    public long nextLong() throws IOException {
        return Long.parseLong(next());
    }

    // Read full line
    public String nextLine() throws IOException {
        return br.readLine();
    }

    // Read double with proper formatting
    public double nextDouble() throws IOException {
        String x = next();
        StringBuilder sb = new StringBuilder("0");
        double res = 0, f = 1;
        boolean dec = false, neg = false;
        int start = 0;
        if (x.charAt(0) == '-') {
            neg = true;
            start++;
        }
        for (int i = start; i < x.length(); i++)
            if (x.charAt(i) == '.') {
                res = Long.parseLong(sb.toString());
                sb = new StringBuilder("0");
                dec = true;
            } else {
                sb.append(x.charAt(i));
                if (dec)
                    f *= 10;
            }
        res += Long.parseLong(sb.toString()) / f;
        return res * (neg ? -1 : 1);
    }
    @Override
    public void close() throws IOException {
        if (br != null) {
            br.close();
        }
    }
    // Check if ready to read
    public boolean ready() throws IOException {
        return br.ready();
    }
}