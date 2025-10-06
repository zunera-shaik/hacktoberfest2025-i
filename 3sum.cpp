class Solution {
public:
    vector<vector<int>> threeSum(vector<int>& nums) {
        // Sort the array first
        sort(nums.begin(), nums.end());
        
        set<vector<int>> uniqueTriplets; // store unique triplets
        vector<vector<int>> output;
        
        for (int i = 0; i < nums.size(); i++) {
            int j = i + 1, k = nums.size() - 1;

            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];

                if (sum == 0) {
                    // Found a valid triplet
                    uniqueTriplets.insert({nums[i], nums[j], nums[k]});
                    j++;
                    k--;
                }
                else if (sum > 0) {
                    k--; // reduce sum
                }
                else {
                    j++; // increase sum
                }
            }
        }

        // Move triplets from set to output vector
        for (auto &triplet : uniqueTriplets) {
            output.push_back(triplet);
        }

        return output;
    }
};
