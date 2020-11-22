Tails
=====

Yep, it's that furry mod, now for Fabric 1.16.2. We'll see where it goes.

## License

MIT license, but please don't vulture over the code and try to fork it/submit PRs while it's half-finished like this, danke.

### quick notes

These are goals, and don't represent what's actually in the code.

* Client only mod, with server component just for syncing.
  * It might be possible to split "client-to-client-syncing" into a reusable library
    * but for now just keep everything inside the mod
* more of a focus on getting things to work, less on extensibility
  * ship a minimum viable product 
  * EDIT BY ME, SEVERAL WEEKS LATER: lmao
* look into GeckoLib for an animation backend?
  * this totally has to be possible right
  * would be super fun to model parts
  * (actually i think this is too hard)
  * (maybe geckocore?)

After finding that a straight port of the original Tails was impossible (rendering changed, and the mod uses a whole TON of magic numbers to position things that are all invalid now), I couldn't help but bring over the "mount point" system from the failed Appendages project. Gave it a little clean-up and it's a lot less messy in this codebase. We'll see how well this works in practice.

* Can I yeet the mountpoint system in favor of the bone system
* actually the mpoint system is good because e.g. it accounts for different player arm widths  