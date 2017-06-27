#include<iostream>
#include<vector>

class Bubble_sorting {

    std::vector<int> numbers;

public:
    Bubble_sorting(std::vector<int> nums) : numbers(nums) {}

    void sort_me() {
        for(int index = 0; index < numbers.size(); index++) {
        for(int j = 1; j < numbers.size() - index; ++j) {
            if (numbers[j] > numbers[j - 1]) {
                int t = numbers[j - 1];
                numbers[j - 1] = numbers[j];
                numbers[j] = t;
            }
        }
        }
    }

    void print_me() {
        for(int h = 0; h < numbers.size(); ++h) {
            std::cout << numbers[h] << " ";
        }
        std::cout << std::endl;
    }

};

// extract functionality into class
int main() {
    int n;
    std::cin >> n;

    std::vector<int> arr(n, 0);
    for(int i = 0; i < n; ++i) {
        std::cin >> arr[i];
    }

    Bubble_sorting bs(arr);
    bs.sort_me();
    bs.print_me();

}


