/*
 * Copyright 2014-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.dbflute.optional;

import java.util.Optional;

import org.dbflute.exception.EntityAlreadyDeletedException;

/**
 * @param <THING> The type of thing.
 * @author jflute
 * @since 1.0.5F (2014/05/05 Monday)
 */
public interface OptionalThing<THING> {

    // ===================================================================================
    //                                                                   Standard Handling
    //                                                                   =================
    /**
     * Handle the wrapped thing if it is present. <br>
     * You should call this if null object handling is unnecessary (do nothing if null). <br>
     * If exception is preferred when null object, use required().
     * @param oneArgLambda The callback interface to consume the optional thing. (NotNull)
     * @return The handler of after process when if not present. (NotNull)
     */
    OptionalThingIfPresentAfter ifPresent(OptionalThingConsumer<THING> oneArgLambda);

    /**
     * Is the object instance present? (existing?)
     * @return The determination, true or false.
     */
    boolean isPresent();

    /**
     * Get the thing or exception if null.
     * @return The instance of the wrapped thing. (NotNull)
     * @throws EntityAlreadyDeletedException When the object instance wrapped in this optional thing is null, which means object has already been deleted (point is not found).
     */
    THING get();

    /**
     * Filter the object by the predicate.
     * @param oneArgLambda The callback to predicate whether the object is remained. (NotNull)
     * @return The filtered optional thing, might be empty. (NotNull)
     */
    OptionalThing<THING> filter(OptionalThingPredicate<THING> oneArgLambda);

    /**
     * Apply the mapping of object to result object.
     * @param <RESULT> The type of mapping result.
     * @param oneArgLambda The callback interface to apply. (NotNull)
     * @return The optional thing as mapped result. (NotNull, EmptyOptionalAllowed: if not present or callback returns null)
     */
    <RESULT> OptionalThing<RESULT> map(OptionalThingFunction<? super THING, ? extends RESULT> oneArgLambda);

    /**
     * Apply the flat-mapping of object to result object.
     * @param <RESULT> The type of mapping result.
     * @param oneArgLambda The callback interface to apply. (NotNull)
     * @return The optional thing as mapped result. (NotNull, EmptyOptionalAllowed: if not present or callback returns null)
     */
    <RESULT> OptionalThing<RESULT> flatMap(OptionalThingFunction<? super THING, OptionalThing<RESULT>> oneArgLambda);

    /**
     * Get the wrapped instance or returns the specified thing.
     * @param other The object instance to be returned when the optional is empty. (NullAllowed)
     * @return The wrapped instance or specified other object. (NullAllowed:)
     */
    THING orElse(THING other);

    /**
     * Get the thing or get from the supplier.
     * @param noArgLambda The supplier of other instance if null. (NotNull)
     * @return The object instance wrapped in this optional thing or specified value. (NullAllowed: if null specified)
     */
    THING orElseGet(OptionalThingSupplier<THING> noArgLambda);

    // unsupported because of almost no needed, alwaysPresent() or get() to use prepared thrower
    // or you can use the method of standard optional instead
    //public ENTITY orElseThrow(...) {
    //    return ...;
    //}

    // ===================================================================================
    //                                                                   Standard Optional
    //                                                                   =================
    /**
     * Convert to Java standard optional class. <br>
     * For only when standard optional handling is needed, so basically you don't use this.
     * @return The new-created instance or empty. (NotNull)
     */
    Optional<THING> toOptional();

    // ===================================================================================
    //                                                                   DBFlute Extension
    //                                                                   =================
    /**
     * Handle the object in the optional thing or exception if not present.
     * @param oneArgLambda The callback interface to consume the optional thing. (NotNull)
     * @throws EntityAlreadyDeletedException When the object instance wrapped in this optional thing is null, which means object has already been deleted (point is not found).
     */
    void alwaysPresent(OptionalThingConsumer<THING> oneArgLambda);

    /**
     * Get the object instance or null if not present. <br>
     * basically use ifPresent() if might be not present, this is for emergency
     * @return The object instance wrapped in this optional thing or null. (NullAllowed: if not present)
     */
    THING orElseNull();
}
