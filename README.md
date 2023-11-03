# Breath of the Minecraft

## Setup

For setup instructions please see the [fabric wiki page](https://fabricmc.net/wiki/tutorial:setup) that relates to the IDE that you are using.

## Building

While in the root of the repository, run `./gradlew genSources` and then `./gradlew build`. The mod will be located at `./build/libs/BotM-[version].jar`.

## Known Bugs

<strike>Mipha's Grace will still make `tryUseTotem` return false, registering the player as dead and as such increase stats and drop items/XP whether or not they have Mipha's Grace in their inventory. Pressing `Respawn` will simply get rid of the overlay and allow the player to continue on.</strike> This bug has been fixed as of november 2023.