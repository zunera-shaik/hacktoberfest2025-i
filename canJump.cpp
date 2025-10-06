class Solution {
public:
    // void help(vector<int>& nums, vector<int>& dp, int high){

    // }
    bool canJump(vector<int>& nums) {
        // vector<int> dp(nums.size(), -1);
        if(nums.size() == 1) return true;

        int jump = nums[0], i = 1;

        while(jump >= 0 && i <= nums.size()-1){
            jump--;
            if(jump < 0) return false;
            jump = max(jump, nums[i]);
            i++;
        }

        return i == nums.size() || i == nums.size()-1;
    }
};
