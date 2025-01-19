# Stickers-auto
![OpenSource](https://img.shields.io/badge/Open%20Source-%E2%99%A5-red)
![Apache 2.0](https://img.shields.io/github/license/dan-sazonov/stickers-auto)
![Tested on linux, Win10](https://img.shields.io/badge/tested%20on-Linux%20|%20Win10-blue)

**Stickers-auto** is a Java application for creating and managing Telegram sticker packs.  
The program allows you to:
1. Create a new sticker pack with the first sticker from the specified directory.
2. Add all stickers from the specified directory to the created sticker pack.
3. Automatically determine the emoji associated with each sticker based on the file name (emojis in the file name).


## 🧳Requirements

- Java 17 or higher
- Gradle (version 7.5 or higher)
- Active Telegram bot (with a token)
- Access to the Telegram Bot API

## 📦Installation
1. Clone the repository:
   ```bash
   git clone git@github.com:dan-sazonov/stickers-auto.git
   ```
2. Navigate to the project directory:
   ```bash
   cd stickers-auto
   ```
3. Install dependencies and build the project:
   ```bash
   gradle build
   ```

## 🛠Configuration
1. Create a `.env` file in the root directory to store the bot token:
   ```
   BOT_TOKEN=<your_bot_token>
   ```
2. Configure the `config.properties` file:
   ```
   botName=example_bot    #the name of your bot (without the @ prefix).
   userId=123456789       #Telegram ID of the user creating the sticker pack.
   ```

## ⚙Usage
Run the application using the following command:
```bash
./gradlew run --console=plain --quiet
```

During execution, you will be prompted to choose a mode of operation:

1. **Create a new sticker pack**:
   - Enter the path to the directory containing the sticker files.
   - Provide the sticker pack name and title.
   - The program will automatically:
      - Extract emojis from file names.
      - Skip files without emojis.
      - Create the sticker pack and add all valid stickers.

2. **Update an existing sticker pack**:
   - Enter the path to the directory containing the new stickers.
   - Provide the name of the existing sticker pack.
   - The program will automatically:
      - Extract emojis from file names.
      - Skip files without emojis.
      - Add all valid stickers to the specified sticker pack.

Example input/output for **Create a new sticker pack**:

```
> Select mode: [1] Create sticker pack, [2] Update sticker pack: 1
> Enter path to the directory containing stickers: /path/to/stickers
> Enter sticker pack name: my_new_pack
> Enter sticker pack title: My Awesome Stickers
Sticker pack created successfully with the first sticker: :smile:
Sticker added successfully: :heart:
Sticker added successfully: :star:
Sticker added successfully: :rocket:
```

Example input/output for **Update an existing sticker pack**:

```
> Select mode: [1] Create sticker pack, [2] Update sticker pack: 2
> Enter path to directory with stickers: /path/to/new_stickers
> Enter sticker pack name to update: my_existing_pack
Sticker added successfully: :fire:
Sticker added successfully: :sunny:
Sticker added successfully: :moon:
```

## 🔬Sources
### 🗂Project Structure

```
src
├── main
│   ├── java
│   │   ├── com.example
│   │   │   ├── Main.java               // Entry point
│   │   │   ├── config
│   │   │   │   └── ConfigLoader.java   // Configuration loader
│   │   │   ├── telegram
│   │   │   │   └── TelegramApiClient.java // Telegram API interaction
│   │   │   ├── utils
│   │   │   │   ├── EmojiExtractor.java // Emoji extraction from file names
│   │   │   │   └── FileValidator.java  // File and directory validation
│   ├── resources
│   │   ├── .env                        // Environment variables
│   │   └── config.properties           // Configuration file
```
### 🎯Features
- [ ] Adapt support for video stickers
- [ ] Implement image adaptation to the required format
- [ ] Create a table with photo paths and their corresponding emojis

### 🐛Fixes
- [ ] Refactor the code
- [ ] Add test coverage
- [ ] Handle errors and edge cases
- [ ] Improve output clarity

### 🤝 Contributing
If you have any ideas or found any bugs here, plz open the [issue](https://github.com/dan-sazonov/stickers-auto/issues)
or make a fork and offer a [pull request](https://github.com/dan-sazonov/stickers-auto/pulls). And it will be
great if you tell me about these ideas, maybe I'm already working on them.

## 👨‍💻 Author
The author of this repository and code - [@dan-sazonov](https://github.com/dan-sazonov). <br>
**Reach me:**<br>
[✈️ Telegram](https://t.me/dan_sazonov) <br>
[📧 Email](mailto:p-294803@yandex.com) <br>