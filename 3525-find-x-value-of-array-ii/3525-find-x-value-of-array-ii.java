class Solution {
    private int n, k;
    private int[] prod;      // prod[node] = product of segment mod k
    private int[][] trans;   // trans[node] = flattened k*k matrix, trans[node][a*k+b]
    private int[] numsArr;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.n = nums.length;
        this.k = k;
        this.numsArr = nums.clone();
        this.prod = new int[4 * n];
        this.trans = new int[4 * n][k * k];

        build(1, 0, n - 1);

        int[] result = new int[queries.length];
        int startMult = 1 % k;

        for (int i = 0; i < queries.length; i++) {
            int idx = queries[i][0];
            int val = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            numsArr[idx] = val;
            update(1, 0, n - 1, idx);

            int[] resProd = new int[1];
            int[] resTrans = query(1, 0, n - 1, start, n - 1, resProd);

            result[i] = resTrans[startMult * k + x];
        }

        return result;
    }

    private void build(int node, int l, int r) {
        if (l == r) {
            int v = numsArr[l] % k;
            prod[node] = v;
            for (int a = 0; a < k; a++) {
                int b = (a * v) % k;
                trans[node][a * k + b] = 1;
            }
            return;
        }
        int mid = (l + r) / 2;
        build(2 * node, l, mid);
        build(2 * node + 1, mid + 1, r);
        merge(node, 2 * node, 2 * node + 1);
    }

    private void update(int node, int l, int r, int idx) {
        if (l == r) {
            int v = numsArr[l] % k;
            prod[node] = v;
            java.util.Arrays.fill(trans[node], 0);
            for (int a = 0; a < k; a++) {
                int b = (a * v) % k;
                trans[node][a * k + b] = 1;
            }
            return;
        }
        int mid = (l + r) / 2;
        if (idx <= mid) update(2 * node, l, mid, idx);
        else update(2 * node + 1, mid + 1, r, idx);
        merge(node, 2 * node, 2 * node + 1);
    }

    private void merge(int node, int leftNode, int rightNode) {
        int pL = prod[leftNode];
        int pR = prod[rightNode];
        prod[node] = (pL * pR) % k;

        int[] tL = trans[leftNode];
        int[] tR = trans[rightNode];
        int[] tN = trans[node];
        java.util.Arrays.fill(tN, 0);

        for (int a = 0; a < k; a++) {
            int shifted = (a * pL) % k;
            for (int b = 0; b < k; b++) {
                tN[a * k + b] = tL[a * k + b] + tR[shifted * k + b];
            }
        }
    }

    private int[] query(int node, int l, int r, int ql, int qr, int[] outProd) {
        if (qr < l || r < ql) {
            outProd[0] = 1 % k;
            return new int[k * k];
        }
        if (ql <= l && r <= qr) {
            outProd[0] = prod[node];
            return trans[node];
        }
        int mid = (l + r) / 2;
        int[] outProdL = new int[1];
        int[] tL = query(2 * node, l, mid, ql, qr, outProdL);
        int[] outProdR = new int[1];
        int[] tR = query(2 * node + 1, mid + 1, r, ql, qr, outProdR);

        int pL = outProdL[0];
        int pR = outProdR[0];
        outProd[0] = (pL * pR) % k;

        int[] tRes = new int[k * k];
        for (int a = 0; a < k; a++) {
            int shifted = (a * pL) % k;
            for (int b = 0; b < k; b++) {
                tRes[a * k + b] = tL[a * k + b] + tR[shifted * k + b];
            }
        }
        return tRes;
    }
}