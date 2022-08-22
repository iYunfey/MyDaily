public class PivotIndex {

    public static void main(String[] args) {
        int[] nums = {1, 7, 3, 6, 5, 6};
        int i = pivotIndex(nums);
        System.out.println(i);
    }

    public static int pivotIndex(int[] nums) {
        int result = -1;
        int leftSum = 0;
        int len = nums.length;
        int rightSum = 0;
        for (int i = 0; i < len; i++) {
            if (i > 0) {
                rightSum += nums[i];
            }
        }
        for (int i = 0; i < len; i++) {
            if (i > 0) {
                rightSum -= nums[i];
            }
            if (leftSum == rightSum) {
                result = i;
                break;
            }
            leftSum += nums[i];
        }

        return result;
    }
}
