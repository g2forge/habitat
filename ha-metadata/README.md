# Habitat Metadata

# Glossary

* Subjects & Predicates
  * Subject, Predicate, Object
  * Present vs absent: whether a predicate is present or absent on a subject.  This is different than `null` for sure.
  * Element, Value: parts of a subject. Talk about element, value type and value as sources of metadata.  The first of these capable of hosting a predicatetype determines the predicate.
  * Merged: when you combine the metadata from two arbitrary subjects
  * Container: a predicate which contains other predicates, see repeatable annotations for an analogy
* Registries: registries are the classes which provide metadata, by returning the accessor which should be used to provide a specific predicate
  * Chained: a registry that calls other registries in order
  * Caching: a registry that caches lookups
  * Indirect: a predicatetype that specifier it's own accessor, allow the predicatetype to control how the predicate is computed
  * Dynamic: metadata computed by the value
  * Computed: registries based on general computation
    * Mixin: arbitrary metadata which is specified remotely from the subject and predicatetype (as opposed to e.g. annotations or indirect metadata)
  * Structural: metadata on an element may be controlled by other elements of program structure, such as metadata about a method may come from a class-level annotation
    * Inherited: Metadata which comes from a super-type
* Mixins
  * Direct specifier: you specify the exact subject or predicate type
  * Typed: you specify based on the type
  * Functional: you specify a mixin based on a boolean test (`IPredicate1`)
  * Copy you specify the value by specifying where to look up the value
* Stereotype: a piece of metadata which implies others, often used to avoid the need to add many related predicates when that group of predicates must be added to many subjects.  Not all predicate type types can be used for stereotypes, for example indirect predicate types are generally unsuitable as they have no way to imform the lookup process for the implied predicate.
