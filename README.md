# liblevenshtein

## Java

### A library for generating Finite State Transducers based on Levenshtein Automata.

[![Maven Central][maven-version-badge]][maven-repo]
[![Reference Status][maven-refs-badge]][maven-refs]
[![License][license-badge]][license]
[![Build Status][build-status-badge]][travis-ci]
[![Coverage Status][coverage-badge]][coveralls]
[![Coverity Scan Build Status][static-analysis-badge]][coverity]
[![Dependency Status][dependency-status-badge]][versioneye]
[![Gitter][gitter-badge]][gitter-channel]

Levenshtein transducers accept a query term and return all terms in a
dictionary that are within n spelling errors away from it. They constitute a
highly-efficient (space _and_ time) class of spelling correctors that work very
well when you do not require context while making suggestions.  Forget about
performing a linear scan over your dictionary to find all terms that are
sufficiently-close to the user's query, using a quadratic implementation of the
[Levenshtein distance][levenshtein-wikipedia] or
[Damerau-Levenshtein distance][damerau-wikipedia], these babies find _all_ the
terms from your dictionary in linear time _on the length of the query term_ (not
on the size of the dictionary, on the length of the query term).

If you need context, then take the candidates generated by the transducer as a
starting place, and plug them into whatever model you're using for context (such
as by selecting the sequence of terms that have the greatest probability of
appearing together).

For a quick demonstration, please visit the [Github Page, here][live-demo].

The library is currently written in Java, CoffeeScript, and JavaScript, but I
will be porting it to other languages, soon.  If you have a specific language
you would like to see it in, or package-management system you would like it
deployed to, let me know.

### Branches

|                            Branch | Description                                 |
| ---------------------------------:|:------------------------------------------- |
|           [master][master-branch] | Latest, development source.                 |
|         [release][release-branch] | Latest, release source.                     |
| [release-2.x][release-2.x-branch] | Latest, release source from the 2.x series. |

### Documentation

When it comes to documentation, you have several options:
- [Wiki][wiki]
- [Javadoc][javadoc]
- [Source Code][tagged-source]

### Basic Usage:

### Minimum Java Version

liblevenshtein has been developed against Java &ge; .  It
will not work with prior versions.

#### Installation

##### Maven

Add a dependency on :: to your project's POM:

```xml
<dependency>
  <groupId></groupId>
  <artifactId></artifactId>
  <version></version>
</dependency>
```

##### Apache Buildr

```ruby
'::jar:'
```

##### Apache Ivy

```xml
<dependency org="" name="" rev="" />
```

##### Groovy Grape

```groovy
@Grapes(
@Grab(group='', module='', version='')
)
```

##### Gradle / Grails

Add a dependency on :: to your project's
`build.gradle`:

```groovy
compile '::'
```

##### Scala SBT

```scala
libraryDependencies += "" % "" % ""
```

##### Leiningen

```clojure
[/ ""]
```

#### Git

You probably don't want this option unless you just want to dig through the
source or help me with maintenance (yes, please!), but if you'd like to checkout
the Git repo, clone it from GitHub:

```
$ git clone https://github.com/universal-automata/liblevenshtein-java.git
Cloning into 'liblevenshtein-java'...
remote: Counting objects: 1570, done.
remote: Compressing objects: 100% (23/23), done.
remote: Total 1570 (delta 4), reused 0 (delta 0), pack-reused 1541
Receiving objects: 100% (1570/1570), 245.49 KiB | 0 bytes/s, done.
Resolving deltas: 100% (664/664), done.
Checking connectivity... done.
```

#### Usage

Once you've checked out the library, use it:

```java
package pkg.of.awesomeness;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.dylon.liblevenshtein.levenshtein.Algorithm;
import com.github.dylon.liblevenshtein.levenshtein.Candidate;
import com.github.dylon.liblevenshtein.levenshtein.ITransducer;
import com.github.dylon.liblevenshtein.levenshtein.factory.TransducerBuilder;

public class GetSpellingCandidates {

  public static void main(final String... args) throws IOException {
    int argsIdx = 0;
    final String dictionaryPath = args[argsIdx ++];
    final int maxDistance = Integer.parseInt(args[argsIdx ++]);

    final Collection<String> dictionary = buildDictionary(dictionaryPath);

    final ITransducer<Candidate> transducer = new TransducerBuilder()
      .algorithm(Algorithm.TRANSPOSITION)
      .defaultMaxDistance(maxDistance)
      .dictionary(dictionary)
      .build();

    for (int i = argsIdx; i < args.length; ++i) {
      final String queryTerm = args[i];
      for (final Candidate candidate : transducer.transduce(queryTerm)) {
        final String candidateTerm = candidate.term();
        final int distance = candidate.distance();
        System.out.printf("d(%s, %s) = %d%n", queryTerm, candidateTerm, distance);
      }
    }
  }

  private static Collection<String> buildDictionary(final String dictionaryPath)
      throws IOException {

    try (final Reader dictionaryReader = new FileReader(dictionaryPath);
         final BufferedReader lineReader = new BufferedReader(dictionaryReader)) {

      final List<String> dictionary = new ArrayList<>();

      String line;
      while (null != (line = lineReader.readLine())) {
        dictionary.add(line);
      }

      return dictionary;
    }
  }
}
```

```sh
# Calls GetSpellingCandidates with the newline-delimited, dictionary of terms, a
# maximum distance of 2, and the following terms to find spelling candidates
# for: foo; bar; baz
java pkg.of.awesomeness.GetSpellingCandidates /path/to/dictionary.txt 2 foo bar baz
```

Please see the [wiki][wiki] for more details.

### Reference

This library is based largely on the work of [Stoyan
Mihov](http://www.lml.bas.bg/~stoyan/), [Klaus
Schulz](http://www.cis.uni-muenchen.de/people/schulz.html), and Petar Nikolaev
Mitankin: "[Fast String Correction with
Levenshtein-Automata](http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.16.652
"Klaus Schulz and Stoyan Mihov (2002)")".  For more details, please see the
[wiki][wiki].

[levenshtein-wikipedia]: https://en.wikipedia.org/wiki/Levenshtein_distance
[damerau-wikipedia]: https://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance

[maven-version-badge]: https://maven-badges.herokuapp.com/maven-central/com.github.dylon/liblevenshtein/badge.svg
[maven-repo]: https://maven-badges.herokuapp.com/maven-central/com.github.dylon/liblevenshtein
[maven-refs-badge]: https://www.versioneye.com/java/com.github.dylon:liblevenshtein/reference_badge.svg
[maven-refs]: https://www.versioneye.com/java/com.github.dylon:liblevenshtein/references
[license-badge]: https://img.shields.io/github/license/universal-automata/liblevenshtein-java.svg
[license]: https://raw.githubusercontent.com/universal-automata/liblevenshtein-java/master/LICENSE
[build-status-badge]: https://travis-ci.org/universal-automata/liblevenshtein-java.svg?branch=master
[travis-ci]: https://travis-ci.org/universal-automata/liblevenshtein-java
[coverage-badge]: https://coveralls.io/repos/github/universal-automata/liblevenshtein-java/badge.svg?branch=master
[coveralls]: https://coveralls.io/github/universal-automata/liblevenshtein-java?branch=master
[static-analysis-badge]: https://img.shields.io/coverity/scan/8476.svg
[coverity]: https://scan.coverity.com/projects/universal-automata-liblevenshtein-java
[dependency-status-badge]: https://www.versioneye.com/user/projects/570345d4fcd19a0051853d99/badge.svg
[versioneye]: https://www.versioneye.com/user/projects/570345d4fcd19a0051853d99
[gitter-badge]: https://badges.gitter.im/universal-automata/liblevenshtein-java.svg
[gitter-channel]: https://gitter.im/universal-automata/liblevenshtein-java?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge

[live-demo]: http://universal-automata.github.io/liblevenshtein/

[wiki]: https://github.com/universal-automata/liblevenshtein/wiki
[javadoc]: http://universal-automata.github.io/liblevenshtein-java/docs/javadoc//index.html   API
[tagged-source]: https://github.com/universal-automata/liblevenshtein-java/tree//src 

[master-branch]: https://github.com/universal-automata/liblevenshtein-java/tree/master
[release-branch]: https://github.com/universal-automata/liblevenshtein-java/tree/release
[release-2.x-branch]: https://github.com/universal-automata/liblevenshtein-java/tree/release-2.x
