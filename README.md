# Image Compressor

An Android image compression application built using **Jetpack Compose** and **Material3**. This project allows users to pick images from their gallery, display the selected image, compress it, and save the compressed version to the Downloads folder. The project follows a modern architecture and clean coding practices, ensuring professional code quality, separation of concerns, and proper state management.

## Features

- Image selection using Android's native media picker.
- Image compression functionality with configurable threshold.
- Automatic saving of compressed images to the Downloads folder.
- Dynamic UI updates with Jetpack Compose, including compression state handling.
- Permissions handling, following modern Android scoped storage policies.
- Toast notifications to provide user feedback on compression success or failure.

## Tech Stack

- **Kotlin** - Main programming language
- **Jetpack Compose** - Modern toolkit for building native UI
- **Material3** - UI components for an up-to-date design
- **Coroutines** - Asynchronous programming and background tasks
- **Android Scoped Storage** - For safe, modern file handling without requiring legacy permissions
- **ActivityResultContracts** - For handling image picking and permissions in a cleaner, more efficient way

## Project Structure

This project is organized to maintain a **clean architecture**:

- `MainActivity`: The main entry point of the application.
- `PhotoPickerScreen`: The composable screen that handles image picking, compression, and user interactions.
- `ImageCompressor`: A helper class for compressing images based on a size threshold.
- `FileManager`: Handles file saving, ensuring images are stored in the appropriate location (Downloads folder).
- `PermissionManager`: Manages permission requests (if necessary).
- `ToastManager`: Displays toasts for user feedback on events like success or failure.


## How to Use

1. Clone the repository:

    ```bash
    git clone https://github.com/sijan8s3/Image-Compressor.git
    ```

2. Open the project in **Android Studio**.

3. Run the project on an Android device (targeting Android 10 or higher).

4. Use the app to select an image, view it, and compress it.

5. Compressed images will be saved in the **Downloads** folder with the name format `filename_compressed.extension`.

## Permissions

This project uses Android's **Scoped Storage** policies, so manual permissions for external storage are no longer required for image picking or saving. If you're targeting Android 9 (API 28) or below, you might need to request storage permissions.

## Installation

To run the project locally:

1. Clone the repository.
2. Open the project in Android Studio.
3. Ensure you have the necessary SDK (Android 10+).
4. Build and run the app on a device or emulator.

## Libraries Used

- **Jetpack Compose**: For declarative UI building.
- **Material3**: For modern UI components and theming.
- **Coil**: For loading and displaying images efficiently.
- **Coroutines**: For handling background tasks and operations.

## Contributing

If you'd like to contribute, feel free to open a pull request or report issues. Contributions are always welcome!


### Author

[Sijan](https://github.com/sijan8s3)

Feel free to reach out if you have any questions or suggestions!
