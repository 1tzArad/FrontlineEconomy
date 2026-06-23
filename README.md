# FrontlineEconomy

A lightweight, feature-rich economy plugin for Minecraft Paper servers.

## Features

- **Balance Management** — Check your balance or view another player's balance
- **Player-to-Player Payments** — Pay nearby players with a GUI confirmation flow
- **Sell System** — Sell items through a drag-and-drop GUI with live price calculation
- **Balance Top** — Paginated leaderboard of the wealthiest players
- **Admin Commands** — Set, add, take, or reset player balances with full audit logging
- **Transaction History** — View detailed transaction logs for any player
- **Economy API** — Hook into the economy from other plugins
- **Sound Effects** — Audio feedback for send/receive actions
- **MiniMessage Formatting** — Rich colors, gradients, and hover/click text support
- **Configurable Messages** — Customize all player-facing text via `messages.yml`

## Requirements

| Dependency | Version |
|---|---|
| [Paper](https://papermc.io/) | 1.21+ |
| [Lombok](https://projectlombok.org/) | 1.18+ (compile-time) |
| [Triumph GUI](https://github.com/TriumphTeam/triumph-gui) | 3.1+ |
| [ShopGUI API](https://github.com/brcdev-minecraft/shopgui-api) | 3.2+ (provided) |
| [VaultAPI](https://github.com/MilkBowl/VaultAPI) | 1.7+ (provided) |

## Installation

1. Download the latest `FrontlineEconomy.jar` from [Releases](https://github.com/your-username/FrontlineEconomy/releases)
2. Place it in your server's `plugins/` directory
3. Restart the server

## Commands

### Player Commands

| Command | Description | Permission |
|---|---|---|
| `/balance [player]` | Check your balance or another player's | `FrontlineEconomy.Player.Balance` |
| `/pay <player> <amount> [reason]` | Pay a nearby player (within 5 blocks) | `FrontlineEconomy.Player.Pay` |
| `/sell` | Open the sell GUI | `FrontlineEconomy.Player.Sell` |
| `/balancetop [page]` | View the richest players leaderboard | `FrontlineEconomy.Player.BalanceTop` |

### Admin Commands

| Command | Description | Permission |
|---|---|---|
| `/ecoadmin set <player> <amount> <reason>` | Set a player's balance | `FrontlineEconomy.Admin.Set` |
| `/ecoadmin add <player> <amount> <reason>` | Add to a player's balance | `FrontlineEconomy.Admin.Add` |
| `/ecoadmin take <player> <amount> <reason>` | Remove from a player's balance | `FrontlineEconomy.Admin.Take` |
| `/ecoadmin reset <player> <reason>` | Reset a player's balance to 0 | `FrontlineEconomy.Admin.Reset` |
| `/ecoadmin transactions <player>` | View a player's transaction history | `FrontlineEconomy.Admin.Transactions` |

### Permission Nodes

```
FrontlineEconomy.Player.Balance
FrontlineEconomy.Player.Pay
FrontlineEconomy.Player.Sell
FrontlineEconomy.Player.BalanceTop
FrontlineEconomy.Admin
FrontlineEconomy.Admin.Set
FrontlineEconomy.Admin.Add
FrontlineEconomy.Admin.Take
FrontlineEconomy.Admin.Reset
FrontlineEconomy.Admin.Transactions
```

## API Usage

Register the economy service from another plugin:

```java
// Get the EconomyAPI instance
RegisteredService<EconomyAPI> service = Bukkit.getServicesManager()
    .getRegistration(EconomyAPI.class);

if (service != null) {
    EconomyAPI economy = service.getProvider();

    double balance = economy.getBalance(playerUuid);
    economy.deposit(playerUuid, 100);
    economy.withdraw(playerUuid, 50);
    boolean canAfford = economy.has(playerUuid, 200);
}
```

## Sellable Items

Items can be sold via the `/sell` command. Default sell prices:

| Item | Price |
|---|---|
| Diamond | $15 |
| Gold Ingot | $10 |
| Iron Ingot | $5 |
| Emerald | $5 |
| Copper Ingot | $7 |
| Lapis Lazuli | $7 |
| Wheat / Carrot / Potato | $2 |
| Cactus / Sugar Cane | $2 |
| Melon / Pumpkin | $4.5 |
| Heavy Core | $50,000 |

Sellable items and prices can be modified in `SellableItem.java`.

## Building from Source

```bash
git clone https://github.com/your-username/FrontlineEconomy.git
cd FrontlineEconomy
mvn clean package
```

The compiled JAR will be in the `target/` directory.

## Project Structure

```
src/main/java/xyz/ItzArad/frontlineEconomy/
├── api/
│   └── EconomyAPI.java              # Public economy interface
├── core/
│   ├── FrontlineEconomy.java        # Main plugin class
│   ├── EconomyService.java          # EconomyAPI implementation
│   ├── Colors.java                  # MiniMessage color utilities
│   ├── SellableItem.java            # Sellable items enum
│   ├── commands/
│   │   ├── BalanceCommand.java
│   │   ├── BalanceTop.java
│   │   ├── PayCommand.java
│   │   ├── SellCommand.java
│   │   ├── AdminCommand.java
│   │   └── AdminCommands/
│   │       ├── BalanceAddCommand.java
│   │       ├── BalanceSetCommand.java
│   │       ├── BalanceTakeCommand.java
│   │       ├── BalanceResetCommand.java
│   │       └── TransactionsCommand.java
│   ├── gui/
│   │   ├── PayGui.java
│   │   ├── SellGui.java
│   │   └── ShopGui.java
│   ├── interfaces/
│   │   ├── AdminCmd.java
│   │   └── AdminTab.java
│   ├── managers/
│   │   ├── EcoManager.java
│   │   ├── EcoAdminManager.java
│   │   ├── TransactionsManager.java
│   │   └── ShopManager.java
│   ├── models/
│   │   ├── EcoPlayer.java
│   │   ├── EcoAdminActionsData.java
│   │   └── TransactionData.java
│   └── utils/
│       ├── JsonStorage.java
│       ├── PlayerUtils.java
│       └── SoundUtils.java
```

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on how to contribute.

## License

This project is open source. See the repository for license details.
