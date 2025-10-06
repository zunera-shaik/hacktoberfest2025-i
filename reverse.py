class Solution(object):
    def reverse(self, x):
        """
        :type x: int
        :rtype: int
        """
        rev=0
        num=x
        if x<0:
            x*=-1   
        while x!=0:
            rem=x%10
            rev=(rev*10)+rem
            x//=10
        if rev>(2**31)-1:
            return 0    
        if num<0:
            return -rev 
        else:
            return rev
