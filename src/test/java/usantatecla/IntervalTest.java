package usantatecla;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntervalTest {

  private Point left = new Point(-2.2);
  private Point right = new Point(4.4);
  private IntervalBuilder intervalBuilder;

  @BeforeEach
  public void before() {
    this.left = new Point(-2.2);
    this.right = new Point(4.4);
    this.intervalBuilder = new IntervalBuilder();
  }

  @Test
  public void givenIntervaOpenOpenlwhenIncludeWithIncludedValueThenTrue() {
    Interval interval = this.intervalBuilder.open(left.getEquals()).open(right.getEquals()).build();
    assertFalse(interval.include(left.getLess()));
    assertFalse(interval.include(left.getEquals()));
    assertTrue(interval.include(left.getGreater()));
    assertTrue(interval.include(right.getLess()));
    assertFalse(interval.include(right.getEquals()));
    assertFalse(interval.include(right.getGreater()));
  }

  @Test
  public void givenIntervaOpenOpenlwhenInc3ludeWithIncludedValueThenTrue() {
    Interval interval = this.intervalBuilder.closed(left.getEquals()).open(right.getEquals()).build();
    assertFalse(interval.include(left.getLess()));
    assertTrue(interval.include(left.getEquals()));
    assertTrue(interval.include(left.getGreater()));

    assertTrue(interval.include(right.getLess()));
    assertFalse(interval.include(right.getEquals()));
    assertFalse(interval.include(right.getGreater()));
  }

  @Test
  public void givenIntervaOpenOpenlwhenIncludeWit3hIncludedValueThenTrue() {
    Interval interval = this.intervalBuilder.open(left.getEquals()).closed(right.getEquals()).build();
    assertFalse(interval.include(left.getLess()));
    assertFalse(interval.include(left.getEquals()));
    assertTrue(interval.include(left.getGreater()));

    assertTrue(interval.include(right.getLess()));
    assertTrue(interval.include(right.getEquals()));
    assertFalse(interval.include(right.getGreater()));
  }

  @Test
  public void givenIntervaOpenOpenlwhenIncludeWithInclude5dValueThenTrue() {
    Interval interval = this.intervalBuilder.closed(left.getEquals()).closed(right.getEquals()).build();
    assertFalse(interval.include(left.getLess()));
    assertTrue(interval.include(left.getEquals()));
    assertTrue(interval.include(left.getGreater()));

    assertTrue(interval.include(right.getLess()));
    assertTrue(interval.include(right.getEquals()));
    assertFalse(interval.include(right.getGreater()));
  }

  @Test
  public void givenIntervalsClosedClosedWhenIncludeNullIsOverlappingThenFalse() {
    Interval interval = this.intervalBuilder.closed(left.getEquals()).closed(right.getEquals()).build();
    assertFalse(interval.isOverlapping(null));
  }

  @Test
  public void givenIntervalClosedClosedNullWhenCreateIntervalThenTrue() {
    Assertions.assertThrows(AssertionError.class, () -> this.intervalBuilder.build());
  }

  /**
   * 
   * Case: -----[----]------
   * 
   * --------------[----]-----
   */
  @Test
  public void givenIntervalsClosedClosedWhenIncludeClosedClosedIsOverlappingThenTrue() {
    Interval interval = this.intervalBuilder.closed(left.getEquals()).closed(right.getEquals()).build();
    Interval otherInterval = new IntervalBuilder().closed(left.getGreater()).closed(right.getGreater()).build();
    assertTrue(interval.isOverlapping(otherInterval));
  }

  /**
   * 
   * Case: -----(----)------
   * 
   * --------------(----)-----
   */
  @Test
  public void givenIntervalsClosedClosedWhenIncludeOpenOpenIsOverlappingThenTrue() {
    Interval interval = this.intervalBuilder.open(left.getEquals()).open(right.getEquals()).build();
    Interval otherInterval = new IntervalBuilder().open(left.getGreater()).open(right.getGreater()).build();
    assertTrue(interval.isOverlapping(otherInterval));
  }

  /**
   * 
   * Case: -----(----)------
   * 
   * -------------------(----)-----
   */
  @Test
  public void givenIntervalsOpenClosedWhenIncludeCloseOpenIsOverlappingThenTrue() {
    Interval interval = this.intervalBuilder.open(left.getEquals()).open(right.getEquals()).build();
    Interval otherInterval = new IntervalBuilder().open(right.getGreater()).open(right.getGreater() + 1).build();
    assertFalse(interval.isOverlapping(otherInterval));
  }

  /**
   * 
   * Case: -----(----)------
   * 
   * ---(----)-----
   */
  @Test
  public void givenIntervalsOpenOpenWhenIncludeOpenOpenIsOverlappingThenTrue() {
    Interval interval = this.intervalBuilder.open(left.getEquals()).open(right.getEquals()).build();
    Interval otherInterval = new IntervalBuilder().open(left.getLess() - 1).open(left.getLess()).build();
    assertFalse(interval.isOverlapping(otherInterval));
  }

  /**
   * 
   * Case: -----(----)------
   * 
   * ------(----)-----
   */
  @Test
  public void givenIntervalsOpenOpenWhenIncludeOpenOpenIsOverlappingSamePointThenTrue() {
    Interval interval = this.intervalBuilder.open(left.getEquals()).open(right.getEquals()).build();
    Interval otherInterval = new IntervalBuilder().open(left.getEquals() - 1).open(left.getEquals()).build();
    assertFalse(interval.isOverlapping(otherInterval));
  }

  /**
   * 
   * Case: -----[----)------
   * 
   * ------(----]-----
   */
  @Test
  public void givenIntervalsClosedOpenWhenIncludeOpenClosedIsOverlappingSamePointThenTrue() {
    Interval interval = this.intervalBuilder.closed(left.getEquals()).open(right.getEquals()).build();
    Interval otherInterval = new IntervalBuilder().open(left.getEquals() - 1).closed(left.getEquals()).build();
    assertTrue(interval.isOverlapping(otherInterval));
  }

  /**
   * 
   * Case: -----[----)------SI
   * 
   * ------(----)-----nO
   */
  @Test
  public void givenIntervalsClosedOpenWhenIncludeOpenOpenIsOverlappingSamePointThenTrue() {
    Interval interval = this.intervalBuilder.closed(left.getEquals()).open(right.getEquals()).build();
    Interval otherInterval = new IntervalBuilder().open(left.getEquals() - 1).open(left.getEquals()).build();
    assertTrue(interval.isOverlapping(otherInterval));
    assertFalse(otherInterval.isOverlapping(interval));

  }
}