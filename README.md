# Tiny Terrors
[![discord-singular](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/social/discord-singular_vector.svg)](https://discord.offsetmonkey538.top/)
[![modrinth](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg)](https://modrinth.com/mod/tiny-terrors)

![All baby mobs added by the mod side by side. From left to right: creeper, enderman, skeleton, stray, bogged, wither skeleton](https://cdn.modrinth.com/data/RpKPCJcY/images/65a6fc66adfac91fd2175b39f5575b8d556164ee.png)
---

You thought baby zombies were bad enough? Wait until you get instantly blown up by a baby creeper

Adds baby versions of creepers, endermen, skeletons, stray, bogged and wither skeletons.  
Baby mobs have a 10% chance of spawning instead of their grown up counterparts.  
Each baby also has a 5% chance of being turned into a chicken jockey.

## Features
- All baby versions
  - Faster run speed
  - Drop more xp
- Creeper
  - Smaller explosion radius
  - Very short fuse
- Enderman
  - Can't pick up blocks
  - Smaller teleportation range
- All skeleton variants (Skeleton, Stray, Bogged, Wither Skeleton)
  - Faster shooting speed
- Everything is fully configurable!

## Configuration
The configuration file for the mod is located at `config/tiny-terrors/main.json`  
The config is split into sections for each of the different baby mobs.

All mobs include the following base config options:
- `speedMultiplier`: percentage of the mob's base speed to add to baby versions
- `xpMultiplier`: multiplied by the mob's base xp amount for getting dropped xp amount for the baby versions
- `spawnChance`: chance of a mob to spawn as the baby version
- `jockeySpawnChacne`: chance of a baby version of a mob to spawn as a chicken jockey

Creepers have the following additional options:
- `fuseTime`: amount of ticks until the baby creeper explodes after getting near a player
- `explosionRadius`: radius of the baby creeper's explosion
- `igniteRadiusMultiplier`: factor to multiply the creeper's base ignite radius with

Endermen have the following additional options:
- `canPickUpBlocks`: If baby endermen should be able to pick up blocks
- `teleportationRangeMultiplier`: factor to multiply the enderman's base teleportation range with

All skeleton variants have the following additional options:
- `bowAttackIntervalMultiplier`: factor to multiply the skeleton's base attack interval with
