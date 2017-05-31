#include<iostream>

// different implementation of bubble sort
int main() {
    int n;
    std::cin >> n;

    int arr[n];
    for(int i = 0; i < n; ++i) {
        std::cin >> arr[i];
    }

    bool was_swapped = true;
    int j = 0;
    int tmp;
    while(was_swapped) {
        was_swapped = false;
        j++;
        for(int i = 0; i < n - j; ++i) {
            if (arr[i] < arr[i + 1]) {
                was_swapped = true;
                int temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }
    }

     for(int i = 0; i < n; ++i) {
        std::cout << arr[i] << " ";
     }
     std::cout << std::endl;
}


