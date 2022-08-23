public class SearchInsert {

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 6};
        int target = 2;
        int i = searchInsert(nums, target);
        System.out.println(i);
    }

    public static int searchInsert(int[] nums, int target) {
        int len = nums.length;
        int left = 0;
        int right = len - 1;
        int middle;
        while (left <= right) {
            middle = (left + right) / 2;
            if (nums[middle] == target) {
                return middle;
            }
            if (nums[middle] < target) {
                left = middle + 1;
            }
            if (nums[middle] > target) {
                right = middle - 1;
            }
        }
        return left;
    }
}
