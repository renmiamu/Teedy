package com.sismics.security;

import com.google.common.collect.Sets;
import com.sismics.docs.core.constant.Constants;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class TestPrincipal {
    @Test
    public void testAnonymousPrincipal() {
        AnonymousPrincipal principal = new AnonymousPrincipal();
        Assert.assertNull(principal.getId());
        Assert.assertEquals(AnonymousPrincipal.ANONYMOUS, principal.getName());
        Assert.assertTrue(principal.isAnonymous());
        Assert.assertFalse(principal.isGuest());
        Assert.assertNull(principal.getEmail());
        Assert.assertTrue(principal.getGroupIdSet().isEmpty());

        DateTimeZone zone = DateTimeZone.forID("UTC");
        principal.setDateTimeZone(zone);
        Assert.assertEquals(zone, principal.getDateTimeZone());
    }

    @Test
    public void testUserPrincipal() {
        UserPrincipal principal = new UserPrincipal("user-1", "alice");
        Assert.assertFalse(principal.isAnonymous());
        Assert.assertFalse(principal.isGuest());
        Assert.assertEquals("user-1", principal.getId());
        Assert.assertEquals("alice", principal.getName());

        principal.setId(Constants.GUEST_USER_ID);
        Assert.assertTrue(principal.isGuest());

        principal.setName("bob");
        principal.setEmail("bob@example.com");
        DateTimeZone zone = DateTimeZone.forID("Asia/Taipei");
        principal.setDateTimeZone(zone);
        Set<String> groups = Sets.newHashSet("g1", "g2");
        principal.setGroupIdSet(groups);
        Set<String> baseFunctions = Sets.newHashSet("f1");
        principal.setBaseFunctionSet(baseFunctions);

        Assert.assertEquals("bob", principal.getName());
        Assert.assertEquals("bob@example.com", principal.getEmail());
        Assert.assertEquals(zone, principal.getDateTimeZone());
        Assert.assertEquals(groups, principal.getGroupIdSet());
        Assert.assertEquals(baseFunctions, principal.getBaseFunctionSet());
    }
}
