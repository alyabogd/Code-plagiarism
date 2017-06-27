#include<iostream>

// here arr is array of int
// n is size of arr (number of digits stored)
void bubble_sort(int *arr, int n) {
    for(int i = 0; i < n; i++) {
        for(int j = 1; j < n - i; ++j) {
            if (arr[j] > arr[j - 1]) {
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
            }
        }
    }
}

// extract sorting into function
int main() {
    int n;
    std::cin >> n;

    int arr[n];
    for(int i = 0; i < n; ++i) {
        std::cin >> arr[i];
    }

    bubble_sort(arr, n);

    for(int i = 0; i < n; ++i) {
       std::cout << arr[i] << " ";
    }
    std::cout << std::endl;
}


