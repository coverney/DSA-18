public class FirstFailingVersion {

    //QUESTION: how is this a greedy problem? it isn't
    public static long firstBadVersion(long n, IsFailingVersion isBadVersion) {
        long hi = n;
        long lo = 1; //start from first version
        while (lo < hi) {
            long mid = (hi + lo) / 2; //compute the middle of the range
            if (isBadVersion.isFailingVersion(mid)) {
                // if the middle has failed then we know all ones after that failed
                // but the goal is to find the first failed version so we decrement hi
                hi = mid;
            } else {
                //if the mid did not fail, then the first failed version would be further down in time
                //must increment lo
                lo = mid + 1;
            }
        }

        //lo and hi meet. if lo is a failed version then that would be the first one
        if (isBadVersion.isFailingVersion(lo)) {
            return lo;
        }
        return -1;
    }
}
